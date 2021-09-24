package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class RequestTag extends SimpleTagSupport {
    private String title;
    private String description;
    private String date;
    private String status;
    private String link;

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

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<div class=\"card h-100 \">");
        out.println("<div class=\"card-body\">");
        out.println("<h5 class=\"card-title\">"+title+"<h5/>");
        out.println("<p class=\"card-text\">"+date+"<p/>");
        out.println("<p class=\"card-text\">"+status+"<p/>");
        out.println("<p class=\"card-text\">"+description+"<p/>");
        out.println("<a href=\""+link+"\" class=\"btn btn-primary\">"+"details"+"</a>");
        out.println("</div>");
        out.println("</div>");
    }
}
