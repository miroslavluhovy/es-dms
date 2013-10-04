package com.github.richardwilly98.esdms.bpm;

import java.util.Date;

import com.github.richardwilly98.esdms.ItemBaseImpl;
import com.github.richardwilly98.esdms.bpm.api.Task;

public class TaskImpl extends ItemBaseImpl implements Task {

    private static final long serialVersionUID = 1L;
    private int priority;
    private Date creation;
    private Date due;
    private String status;
    private String owner;
    private String assignee;
    private String processInstanceId;

    public static class Builder extends BuilderBase<Builder> {

        private int priority;
        private Date creation;
        private Date due;
        private String status;
        private String owner;
        private String assignee;
        private String processInstanceId;

        public Builder priority(int priority) {
            this.priority = priority;
            return getThis();
        }
        public Builder creation(Date creation) {
            this.creation = creation;
            return getThis();
        }
        public Builder status(String status) {
            this.status = status;
            return getThis();
        }
        public Builder owner(String owner) {
            this.owner = owner;
            return getThis();
        }
        public Builder assignee(String assignee) {
            this.assignee = assignee;
            return getThis();
        }
        public Builder processInstanceId(String processInstanceId) {
            this.processInstanceId = processInstanceId;
            return getThis();
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        public Task build() {
            return new TaskImpl(this);
        }

    }

    TaskImpl() {
        super(null);
    }

    protected TaskImpl(Builder builder) {
        super(builder);
        if (builder != null) {
            this.priority = builder.priority;
            this.creation= builder.creation ;
            this.due = builder.due;
            this.status= builder.status;
            this.owner = builder.owner;
            this.assignee = builder.assignee;
            this.processInstanceId = builder.processInstanceId;

        }
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getPriority()
     */
    @Override
    public int getPriority() {
        return priority;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setPriority(int)
     */
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getCreation()
     */
    @Override
    public Date getCreation() {
        return creation;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setCreation(java.util.Date)
     */
    @Override
    public void setCreation(Date creation) {
        this.creation = creation;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getDue()
     */
    @Override
    public Date getDue() {
        return due;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setDue(java.util.Date)
     */
    @Override
    public void setDue(Date due) {
        this.due = due;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getStatus()
     */
    @Override
    public String getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setStatus(java.lang.String)
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getOwner()
     */
    @Override
    public String getOwner() {
        return owner;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setOwner(java.lang.String)
     */
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getAssignee()
     */
    @Override
    public String getAssignee() {
        return assignee;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setAssignee(java.lang.String)
     */
    @Override
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#getProcessInstanceId()
     */
    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /* (non-Javadoc)
     * @see com.github.richardwilly98.esdms.Task#setProcessInstanceId(java.lang.String)
     */
    @Override
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
