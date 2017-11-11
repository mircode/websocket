package websocket;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WsFileHandler extends AbstractWebSocketHandler  {
	private static final Logger logger=Logger.getLogger(WsFileHandler.class);
	
	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		String msg=message.getPayload();
		logger.debug("WebSocket-消息"+msg);
		session.sendMessage(new TextMessage("文本"+msg));
	}
	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) throws Exception {
		byte[] buff = message.getPayload().array();
		logger.debug("WebSocket-消息"+buff);
		save(buff);
		session.sendMessage(new TextMessage("二进制流"+buff.length));
	}
	private void save(byte[] buff) throws Exception {
		File file=new File("D://save.txt");
		file.deleteOnExit();
		FileOutputStream out=new FileOutputStream(file);
		out.write(buff);
		out.close();
	}
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		logger.debug("WebSocket-链接"+session);
	}
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		logger.debug("WebSocket-异常"+session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		logger.debug("WebSocket-关闭"+session);
	}

}
