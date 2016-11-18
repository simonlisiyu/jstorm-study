package test;

/**
 * Created by lisiyu on 2016/11/10.
 */
public class B extends A{
    int age;
    @Override
    public void sayName(){
        System.out.println(name);
        System.out.println(age);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
