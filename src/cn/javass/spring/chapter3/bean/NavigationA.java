package cn.javass.spring.chapter3.bean;


public class NavigationA {
    private NavigationB navigationB;

    public void setNavigationB(NavigationB navigationB) {
        this.navigationB = navigationB;
    }
    public NavigationB getNavigationB() {
        return navigationB;
    }
}
