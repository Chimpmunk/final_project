package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestPageTag extends SimpleTagSupport {
    private String title;
    private String time;
    private String author;
    private String price;
    private String status;
    private String repairman;
    private String description;
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepairman() {
        return repairman;
    }

    public void setRepairman(String repairman) {
        this.repairman = repairman;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Locale loc = new Locale(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", loc);
        JspWriter out = getJspContext().getOut();
        Pattern dateP = Pattern.compile("(\\d+)-(\\d+)-(\\d+)T(.+)");
        Matcher matcher = dateP.matcher(time);
        matcher.find();
        out.println("<div class=\"row m-2 justify-content-between\">");
        out.println("<div>");
        out.println("<h4>"+title+"</h4>");
        out.println("</div>");
        out.println("<div>");
        out.println("<h4>"+matcher.group(4)+" "+matcher.group(3)+"."+matcher.group(2)+"."+matcher.group(1)+"</h4>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class=\"m-2\">");
        out.println("<h2>"+author+"</h2>");

        out.println("<h4>"+price+"</h4>");
        out.println("<h4>"+bundle.getString("status")+": "+status+"</h4>");
        if(repairman!=null && !repairman.equals("")){
            out.println("<h4>"+repairman+"</h4>");
        }


        out.println("<p>"+description+"</p>");
        out.println("</div>");
    }
}
