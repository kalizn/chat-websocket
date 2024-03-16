package br.com.kalizn.chat.models;

public class ChatMessageModel {
    
    private MessageType type;
    private String content;
    private String sender;

    private ChatMessageModel() {} // Construtor privado para impedir instâncias diretas
    
    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
    
    public static class Builder {
        private MessageType type;
        private String content;
        private String sender;
        
        public Builder() {}
        
        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }
        
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        
        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }
        
        public ChatMessageModel build() {
            ChatMessageModel message = new ChatMessageModel();
            message.type = this.type;
            message.content = this.content;
            message.sender = this.sender;
            return message;
        }
    }
}
