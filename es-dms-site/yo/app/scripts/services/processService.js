'use strict';

angular.module('esDmsSiteApp')
  .service('processService', ['$log', '$resource', '$http', function ProcessService($log, $resource, $http) {
    
    var ProcessResource = $resource('api/process/:action', {}, {
      listProcessDefinitions: {method:'GET', params: {action: 'process-definitions'}, isArray: true},
      startProcessInstance: {method:'POST', params: {action: 'process-instances'}}
    });
    
    var ProcessInstancesResource = $resource('api/process/process-instances/:id/:action', {id:'@id'}, {
      listTaskByProcessInstance: {method:'GET', params: {action: 'tasks'}, isArray: true},
      attach: {method:'POST', params: {action: 'process-instances/:id/attachments/:itemId'}}
    });
    
    var TasksResource = $resource('api/process/tasks/:id', {id:'@id'}, {
      assignTask: {method:'POST', params: {}}
    });

    var processDefinitions = [];
    return {
      listProcessDefinitions: function(callback) {
        $log.log('Get process definitions');
        if (processDefinitions.length === 0) {
          var definitions = ProcessResource.listProcessDefinitions(function() {
            // $log.log('get definitions: ' + JSON.stringify(definitions));
            processDefinitions = definitions;
            callback(definitions);
          });
        } else {
          callback(processDefinitions);
        }
      },
      startProcessInstance: function(id, callback) {
        $log.log('Start process instance from process definition: ' + id);
        var instance = new ProcessResource();
        instance.processDefinitionId = id;
        instance.$startProcessInstance({}, function(value, headers) {
          // $log.log('*** startProcessInstance: ' + JSON.stringify(value));
          var location = headers('location');
          // $log.log('*** location: ' + location);
          var id = _.last(location.split('/'));
          instance.id = id;
          callback(instance);
        });
      },
      listTaskByProcessInstance: function(id, callback) {
        $log.log('Get tasks by process instance id: ' + id);
        if (id === undefined) {
          return;
        }
        var tasks = ProcessInstancesResource.listTaskByProcessInstance({'id': id}, function() {
          callback(tasks);
        });
      },
      assignTask: function(id, userId, callback) {
        $log.log('Assign user: ' + userId + ' to tasks: ' + id);
        if (id === undefined || userId === undefined) {
          return;
        }
        var task = new TasksResource();
        task.id = id;
        task.assignee = userId;
        task.$assignTask({}, function(data) {
          callback(data);
        });
      },
      attach: function(id, itemId, callback) {
        $log.log('Attach document: ' + itemId + ' to process: ' + id);
        if (id === undefined || itemId === undefined) {
          return;
        }
        $http.post('api/process/process-instances/'+id+'/attachments/'+itemId, {}).success(function() {
          callback();
        });
      }
    };
  }]);
