package com.revature.model;

import java.util.Objects;

public class Reimbursement {

    private int reimburseId;
    private String reimburseType;

    private String reimburseDesc;
    private float amount;
    private String status;
    private int employeeId;
    private int managerId;

    public Reimbursement(){
    }

    public int getReimburseId() {
        return reimburseId;
    }

    public String getReimburseType() {
        return reimburseType;
    }

    public String getReimburseDesc() {
        return reimburseDesc;
    }

    public float getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setReimburseId(int reimburseId) {
        this.reimburseId = reimburseId;
    }

    public void setReimburseType(String reimburseType) {
        this.reimburseType = reimburseType;
    }

    public void setReimburseDesc(String reimburseDesc) {
        this.reimburseDesc = reimburseDesc;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return reimburseId == that.reimburseId && Float.compare(that.amount, amount) == 0 && employeeId == that.employeeId && managerId == that.managerId && Objects.equals(reimburseType, that.reimburseType) && Objects.equals(reimburseDesc, that.reimburseDesc) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimburseId, reimburseType, reimburseDesc, amount, status, employeeId, managerId);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimburseId=" + reimburseId +
                ", reimburseType='" + reimburseType + '\'' +
                ", reimburseDesc='" + reimburseDesc + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", employeeId=" + employeeId +
                ", managerId=" + managerId +
                '}';
    }

    public Reimbursement(int reimburseId, String reimburseType, String reimburseDesc, float amount, String status, int employeeId, int managerId) {
        this.reimburseId = reimburseId;
        this.reimburseType = reimburseType;
        this.reimburseDesc = reimburseDesc;
        this.amount = amount;
        this.status = status;
        this.employeeId = employeeId;
        this.managerId = managerId;
    }
}
