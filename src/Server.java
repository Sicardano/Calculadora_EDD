
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Servidor HTTP para conectar HTML con la calculadora Java.
 */
public class Server {

    /** Servidor HTTP de la aplicación. */
    private static HttpServer hs;

    /** Variables globales para procesar la petición. */
    private static String q, x, y, op, rs;

    /** Fragmentos de los parámetros HTTP. */
    private static String[] pa, kv;

    /** Índice para recorrer los parámetros. */
    private static int i;

    /**
     * Inicia el servidor HTTP en el puerto 8080.
     *
     * @param ar Argumentos de ejecución.
     * @throws IOException Si falla el servidor.
     */
    public static void main(String[] ar) throws IOException {
        hs = HttpServer.create(new InetSocketAddress(8080), 0);

        hs.createContext("/", Server::ho);
        hs.createContext("/calc", Server::ca);

        hs.start();

        System.out.println("Servidor iniciado en http://localhost:8080");
    }

    /**
     * Envía el archivo HTML principal al navegador.
     *
     * @param ex Petición HTTP recibida.
     * @throws IOException Si falla la lectura del archivo.
     */
    private static void ho(HttpExchange ex) throws IOException {
        if (!ex.getRequestURI().getPath().equals("/")) {
            se(ex, 404, "<h1>Pagina no encontrada</h1>");
            return;
        }

        if (!Files.exists(Path.of("index.html"))) {
            se(ex, 500, "<h1>No se encuentra index.html</h1>");
            return;
        }

        se(ex, 200, Files.readString(Path.of("index.html")));
    }

    /**
     * Recibe los datos enviados por el formulario HTML.
     * Valida los números y ejecuta la operación seleccionada.
     * El método es synchronized para proteger las variables
     * globales compartidas entre peticiones.
     *
     * @param ex Petición HTTP recibida.
     * @throws IOException Si falla la respuesta HTTP.
     */
    private static synchronized void ca(HttpExchange ex)
            throws IOException {

        if (!ex.getRequestMethod().equals("GET")) {
            se(ex, 405, "<h1>Metodo no permitido</h1>");
            return;
        }

        x = null;
        y = null;
        op = null;

        q = ex.getRequestURI().getRawQuery();

        if (q != null) {
            pa = q.split("&");

            for (i = 0; i < pa.length; i++) {
                kv = pa[i].split("=", 2);

                if (kv.length == 2) {
                    switch (kv[0]) {
                        case "a":
                            x = URLDecoder.decode(
                                    kv[1], StandardCharsets.UTF_8);
                            break;

                        case "b":
                            y = URLDecoder.decode(
                                    kv[1], StandardCharsets.UTF_8);
                            break;

                        case "op":
                            op = URLDecoder.decode(
                                    kv[1], StandardCharsets.UTF_8);
                            break;
                    }
                }
            }
        }

        try {
            if (x == null || y == null
                    || !x.matches("-?\\d+")
                    || !y.matches("-?\\d+")) {
                throw new IllegalArgumentException();
            }

            Calculator.a = Integer.parseInt(x);
            Calculator.b = Integer.parseInt(y);

            if (Calculator.a < -99999 || Calculator.a > 99999
                    || Calculator.b < -99999 || Calculator.b > 99999) {
                throw new IllegalArgumentException();
            }

            if (op == null) {
                throw new IllegalArgumentException();
            }

            switch (op) {
                case "su":
                    rs = Calculator.su();
                    break;

                case "re":
                    rs = Calculator.re();
                    break;

                case "mu":
                    rs = Calculator.mu();
                    break;

                case "po":
                    rs = Calculator.po();
                    break;

                default:
                    throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException er) {
            rs = "Error: introduce enteros entre -99999 y 99999";
        }

        se(ex, 200,
                "<!DOCTYPE html>"
                        + "<html lang='es'>"
                        + "<head><meta charset='UTF-8'>"
                        + "<title>Resultado</title></head>"
                        + "<body>"
                        + "<h1>Resultado</h1>"
                        + "<p>" + rs + "</p>"
                        + "<a href='/'>Volver a la calculadora</a>"
                        + "</body></html>");
    }

    /**
     * Envía una respuesta HTTP al navegador.
     *
     * @param ex Petición HTTP.
     * @param sc Código de estado HTTP.
     * @param tx Contenido HTML de la respuesta.
     * @throws IOException Si falla el envío.
     */
    private static void se(HttpExchange ex, int sc, String tx)
            throws IOException {

        ex.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8");

        ex.sendResponseHeaders(
                sc, tx.getBytes(StandardCharsets.UTF_8).length);

        ex.getResponseBody().write(
                tx.getBytes(StandardCharsets.UTF_8));

        ex.close();
    }
}
