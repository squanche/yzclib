package com.yangzhichao.yzclib.enums;


import java.util.*;


public enum TaskStatus {
    /**
     *
     */
    SUCCESS(0, "成功"),
    /**
     *
     */
    ING(1, "进行中"),
    /**
     *
     */
    FAIL(2, "失败");
    Integer code;
    String msg;
    static Map<Integer, TaskStatus> map = new HashMap<>(3);
    static Set<Integer> NOMAL_STATUS = new HashSet(8);


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    TaskStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    static {
        for (TaskStatus status : TaskStatus.values()) {
            map.put(status.getCode(), status);
        }
        NOMAL_STATUS.add(TaskStatus.ING.getCode());
        NOMAL_STATUS.add(TaskStatus.SUCCESS.getCode());
    }

    /**
     * 根据状态获取枚举元数
     *
     * @param code
     * @return
     */
    public static TaskStatus getStatus(Integer code) {
        return map.get(code);
    }


    public static void main(String[] args) {
        TaskStatus a = TaskStatus.getStatus(1);
        System.out.println(a.msg);
        TaskStatus.NOMAL_STATUS.forEach(b->{
            System.out.println(b);
        });

    }


}
