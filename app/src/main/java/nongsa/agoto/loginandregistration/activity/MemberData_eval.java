package nongsa.agoto.loginandregistration.activity;

/**
 * Created by Seong Ho-Kyeong on 2017-05-04.
 */


public class MemberData_eval {

    String teacher;
    String writer;
    int star;
    String dat;
    String review;

    public MemberData_eval(String teacher, String writer, int star, String dat, String review)  {
        // TODO Auto-generated constructor stub

        //생성자함수로 전달받은 Member의 정보를 멤버변수에 저장..
        this.teacher= teacher;
        this.writer=writer;
        this.star=star;
        this.dat=dat;
        this.review=review;
    }
    //이 아래는 getter , setter 메소드듭입니다.
    //OOP(객체 지향 프로그래밍)은 클래스 객체 외부에서 직접 엠머변수에 접근하는 것을 지양합니다.
    //객체의 멤버변수 제어는 객체 스스로 하도록 해서 재사용성을 높인 방법이죠.
    //getter, setter 멤버 메소드들은 그 목적으로 만들어 진 것이죠.


    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
