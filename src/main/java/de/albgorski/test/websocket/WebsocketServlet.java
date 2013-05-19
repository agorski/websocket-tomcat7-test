package de.albgorski.test.websocket;

import com.google.gson.Gson;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Random;


public class WebsocketServlet extends WebSocketServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void init() throws ServletException {
    super.init();
  }


  @Override
  protected StreamInbound createWebSocketInbound(String subProtocol,
                                                 HttpServletRequest request) {
    return new TestowyMessageInbound();
  }

  private static final class TestowyMessageInbound extends MessageInbound {

    public TestowyMessageInbound() {
      super();
      setByteBufferMaxSize(2097152);
      setCharBufferMaxSize(2097152);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
      getWsOutbound().writeBinaryMessage(message);
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
      DaneDto zapytanieKlienta = konwertujDaneZapytania(message.toString());
      Random r = new Random();
      for (int i = 1; i < 11; i++) {
        long losoweOpoznienie = (long) (r.nextDouble() * 2000);
        try {
          Thread.sleep(losoweOpoznienie);
        } catch (InterruptedException e) {
          // nie rob nic
        }
        String text = wygenerujTestowaOdpowiedz("Odpowiedz nr. " + i, "opoznienie " + losoweOpoznienie + " ms; dane z zapytania : " + zapytanieKlienta.toString());
        getWsOutbound().writeTextMessage(CharBuffer.wrap(text));

      }
      getWsOutbound().flush();

      getWsOutbound().close(0, null);
    }

    private String wygenerujTestowaOdpowiedz(String nazwa, String opis) {
      return new Gson().toJson(new DaneDto(nazwa, opis));
    }

    private DaneDto konwertujDaneZapytania(String daneJakoJsonString) {
      return new Gson().fromJson(daneJakoJsonString, DaneDto.class);
    }

  }
}