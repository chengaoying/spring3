package cn.javass.spring.chapter3.bean;

public class CircleB {

    private CircleC circleC;
    
    public CircleB() {
    }
    
    public CircleB(CircleC circleC) {
        this.circleC = circleC;
    }
    
    public void setCircleC(CircleC circleC) {
        this.circleC = circleC;
    }
    public void b() {
        circleC.c();
    }
}
