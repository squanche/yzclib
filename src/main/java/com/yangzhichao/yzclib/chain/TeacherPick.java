package com.yangzhichao.yzclib.chain;

public class TeacherPick {


    private Student boss;

    public TeacherPick(Student s) {
        this.boss = s;
    }


    public void pick(String name) {
        boss.pick(name);


    }

    public static void main(String[] args) {
        StudentA a = new StudentA();

        StudentB b = new StudentB();

        StudentBoss studentBoss = new StudentBoss();
        a.setHuman(b);
        studentBoss.setHuman(a);

        TeacherPick teacherPick=new TeacherPick(studentBoss);
        teacherPick.pick("B");






    }


}
