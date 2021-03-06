import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class JsonServlet extends HttpServlet {

    private Gson gson;

    public JsonServlet() {
        this.gson = Container.getInstance().getGson();
    }

    protected void jsonResponse(HttpServletResponse resp, Object response) throws IOException {
        resp.setHeader("Content-Type", "application/json");

        String jsonString = gson.toJson(response);
        resp.getWriter().write(jsonString);
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    protected <T> T jsonRequest(HttpServletRequest request, Class<T> clazz) throws IOException {
        String body = new String(request.getInputStream().readAllBytes());
        return gson.fromJson(body,clazz);
    }
}
