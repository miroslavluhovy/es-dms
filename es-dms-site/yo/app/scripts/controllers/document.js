'use strict';

esDmsSiteApp.controller('DocumentCtrl', ['$log', '$scope', '$modal', 'documentService', 'searchService', 'sharedService', 'messagingService',
  function ($log, $scope, $modal, documentService, searchService, sharedService, messagingService) {

  $scope.documents = [];
  $scope.facet = null;
  $scope.facets = [];
  $scope.totalHits = 0;
  $scope.elapsedTime = 0;
  $scope.maxPages = 10;
  $scope.totalPages = 0;
  $scope.currentPage = 1;
  $scope.newtag = {};
  $scope.terms = [];
  $scope.service = sharedService;
  $scope.pageSize = $scope.service.getSettings().user.pageSize;

  function init() {
  }

  init();

  $scope.$watch('service.getUserSettings()',
    function(newValue) {
      $log.log('watch - pageSize: ' + newValue.pageSize);
      if (newValue && $scope.pageSize !== newValue.pageSize) {
        $scope.pageSize = newValue.pageSize;
        $scope.search();
      }
    });

  $scope.search = function(/*term*/) {
		$log.log('search');
		if ($scope.criteria === undefined || $scope.criteria === '' || $scope.criteria === '*') {
      messagingService.push({'type': 'info', 'title': 'Search', 'content': 'Empty or wildcard not allowed', 'timeout': 2000});
      $scope.totalHits = 0;
      $scope.totalPages = 0;
			$scope.documents = [];
      $scope.terms = [];
      $scope.facets = [];
      searchService.criteria(null);
		} else {
      $scope.documents = [];
      $scope.terms = [];
      $scope.facets = [];
      searchService.criteria($scope.criteria);
			// $scope.facet = 'tags';
      $scope.facet = 'versions.file.language';
			find(0, $scope.criteria, true);
		}
  };

  function find(first, criteria, updatePagination) {
		$log.log('find - terms: ' + $scope.terms);
		var filters = getFilter(/*term*/);
		searchService.facetedSearch(first, $scope.pageSize, criteria, $scope.facet, filters, function(result) {
			if (updatePagination) {
				setPagination(result);
			}

      $scope.documents = result.items;
      $scope.totalHits = result.totalHits;
      if ($scope.totalHits === 0) {
        $scope.facets = [];
        messagingService.push({'type': 'info', 'title': 'Search', 'content': 'No document found', 'timeout': 2000});
      } else {
        $scope.elapsedTime = result.elapsedTime;
        $scope.facets = result.facets[$scope.facet];

        // Mark as selected the terms
        _.each($scope.facets.terms, function(term){
          term.selected = (_.contains($scope.terms, term.term));
        });
      }
		});
  }

  function getFilter() {
		if ($scope.facet === undefined || $scope.terms === [] || $scope.terms.length === 0) {
			return null;
		}
		var filter = {};
		filter[$scope.facet] = $scope.terms;
		return filter;
  }

  function setPagination(result) {
		var pageSize = result.pageSize;
		var totalHits = result.totalHits;
		var firstIndex = result.firstIndex;
		$scope.totalPages = Math.ceil(totalHits / pageSize);
		$scope.currentPage = 1 + (firstIndex / pageSize);
		$log.log('totalPages: ' + $scope.totalPages + ' - currentPage: ' + $scope.currentPage);
  }

	function getDocument(id) {
		var documents = $scope.documents;
		for (var i in documents) {
			if (documents[i].id === id) {
				return documents[i];
			}
		}
	}

	function getIndexOf(id) {
		for (var i in $scope.documents) {
			if ($scope.documents[i].id === id) {
				return i;
			}
		}
	}

  // Update facets
  function updateFacets(operation, tag) {
    var term = _.find($scope.facets.terms, {'term': tag});
    if ('add' === operation) {
      if (term !== undefined) {
        term.count++;
      } else {
        $scope.facets.terms.push({'term': tag, 'count': 1});
      }
    } else if ('remove' === operation) {
      if (term !== undefined) {
        if (term.count > 1) {
          term.count--;
        } else {
          $scope.facets.terms.splice(_.indexOf($scope.facets.terms, term), 1);
        }
      }
    }
  }
  
  $scope.setPage = function () {
		$log.log('setPage');
		if ($scope.criteria === undefined ) {
			return;
		}
    find( ($scope.currentPage - 1) * $scope.pageSize, $scope.criteria );
  };

  $scope.$watch('currentPage', $scope.setPage );

  $scope.$on('document:addtag', function(evt, args) {
		if (args.id === undefined || args.tag === undefined) {
			return;
		}
		$log.log('*** addTag: ' + args.id + ' - ' + args.tag);
		var id = args.id;
		var tag = args.tag;
		documentService.addTag(id, tag, function(doc) {
			var index = getIndexOf(id);
      // Update facets
      updateFacets('add', tag);
			$scope.documents[index] = doc;
		});
  });

  $scope.$on('document:removetag', function(evt, args) {
		if (args.id === undefined || args.tag === undefined) {
			return;
		}
		$log.log('*** removetag: ' + args.id + ' - ' + args.tag);
		var id = args.id;
		var tag = args.tag;
		documentService.removeTag(id, tag, function(doc) {
			var index = getIndexOf(id);
      updateFacets('remove', tag);
			$scope.documents[index] = doc;
		});
  });

  $scope.$on('search:applyfacet', function(evt, args) {
		if (args.term === undefined || args.selected === undefined) {
			return;
		}
		$log.log('*** applyfacet: ' + args.term + ' - ' + args.selected);
		if (args.selected) {
			$scope.terms.push(args.term);
		} else {
			for (var i in $scope.terms) {
				if ($scope.terms[i] === args.term) {
					$scope.terms.splice(i, 1);
					break;
				}
			}
		}
		find(0, $scope.criteria, true);
  });

  $scope.showDetails = function(id) {
    documentService.showDetails(id);
    $scope.documentId = id;
    $modal.open({
      templateUrl: 'views/documents/details.html',
      controller: 'DocumentsDetailsCtrl',
      resolve: {
        documentId: function() {
          return $scope.documentId;
        }
      }
    });
  };

  $scope.checkout = function(id) {
		documentService.checkout(id);
		var document = getDocument(id);
		if (document) {
			document.attributes.status = 'L';
		}
  };

  $scope.checkin = function(id) {
		documentService.checkin(id);
		var document = getDocument(id);
		if (document) {
			document.attributes.status = 'A';
		}
  };

  $scope.remove = function(id) {
		documentService.remove(id, function(data, status) {
      $log.log('remove -> ' + data + ' - ' + status);
      var document = getDocument(id);
      _.each(document.tags, function (tag) {
        updateFacets('remove', tag);
      });
      var index = getIndexOf(id);
      if (index) {
        $scope.documents.splice(index, 1);
      }
    });
  };

}]);
