// 为了给 updateByCondition 传两个 RecordInput

package me.ihint.datahelper.demo.pojo;

public class RecordsInput {
    private RecordInput target;
    private RecordInput condition;

    public RecordsInput() {
    }

    public RecordInput getTarget() {
        return target;
    }

    public void setTarget(RecordInput target) {
        this.target = target;
    }

    public RecordInput getCondition() {
        return condition;
    }

    public void setCondition(RecordInput condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "(" + target.toString() + ") << (" + condition.toString() + ")";
    }
}
