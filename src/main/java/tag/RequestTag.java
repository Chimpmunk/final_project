package tag;

import com.oracle.wls.shaded.org.apache.bcel.generic.LDIV;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestTag extends SimpleTagSupport {
    private String title;
    private String description;
    private String date;
    private String status;
    private String link;
    private String locale;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public void doTag() throws JspException, IOException {
        Locale loc = new Locale(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", loc);
        JspWriter out = getJspContext().getOut();
        Pattern dateP = Pattern.compile("(\\d+)-(\\d+)-(\\d+)T(.+)");
        Matcher matcher = dateP.matcher(date);
        matcher.find();
        out.println("<div class=\"card mb-2\" style=\"width: 100%\">");
        out.println("<div class=\"card-body\">");
        out.println("<div class=\"row mr-3 ml-3 justify-content-between\">");
        out.println("<h5 class=\"card-title\">"+title+"<h5/>");
        out.println("<p class=\"card-text\">"+matcher.group(4)+" "+matcher.group(3)+"."+matcher.group(2)+"."+matcher.group(1)+"<p/>");
        out.println("</div>");
        out.println("<div class=\"row mr-3 ml-3\"");
        out.println("<p class=\"card-text\">"+bundle.getString("status")+" :"+bundle.getString(status)+"<p/>");
        out.println("<p class=\"card-text\">"+description+"<p/>");

        out.println("</div>");
        out.println("<a href=\""+link+"\" class=\"btn ml-3 btn-primary\">"+"details"+"</a>");
        out.println("</div>");
        out.println("</div>");
    }
}
