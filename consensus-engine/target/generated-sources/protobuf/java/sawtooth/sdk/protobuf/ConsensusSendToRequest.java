// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consensus.proto

package sawtooth.sdk.protobuf;

/**
 * <pre>
 * Send a consensus message to a specific, connected peer
 * </pre>
 *
 * Protobuf type {@code ConsensusSendToRequest}
 */
public  final class ConsensusSendToRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ConsensusSendToRequest)
    ConsensusSendToRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConsensusSendToRequest.newBuilder() to construct.
  private ConsensusSendToRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConsensusSendToRequest() {
    content_ = com.google.protobuf.ByteString.EMPTY;
    messageType_ = "";
    receiverId_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ConsensusSendToRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {

            content_ = input.readBytes();
            break;
          }
          case 18: {

            receiverId_ = input.readBytes();
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            messageType_ = s;
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusSendToRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusSendToRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.ConsensusSendToRequest.class, sawtooth.sdk.protobuf.ConsensusSendToRequest.Builder.class);
  }

  public static final int CONTENT_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString content_;
  /**
   * <pre>
   * Payload to send to peer
   * NOTE: This payload will be wrapped up in a ConsensusPeerMessage struct,
   * which includes computing its SHA-512 digest, inserting this engine's
   * registration info, and the validator's public key, and signing everything
   * with the validator's private key.
   * </pre>
   *
   * <code>bytes content = 1;</code>
   */
  public com.google.protobuf.ByteString getContent() {
    return content_;
  }

  public static final int MESSAGE_TYPE_FIELD_NUMBER = 3;
  private volatile java.lang.Object messageType_;
  /**
   * <code>string message_type = 3;</code>
   */
  public java.lang.String getMessageType() {
    java.lang.Object ref = messageType_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      messageType_ = s;
      return s;
    }
  }
  /**
   * <code>string message_type = 3;</code>
   */
  public com.google.protobuf.ByteString
      getMessageTypeBytes() {
    java.lang.Object ref = messageType_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      messageType_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int RECEIVER_ID_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString receiverId_;
  /**
   * <pre>
   * Peer that this message is destined for
   * </pre>
   *
   * <code>bytes receiver_id = 2;</code>
   */
  public com.google.protobuf.ByteString getReceiverId() {
    return receiverId_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!content_.isEmpty()) {
      output.writeBytes(1, content_);
    }
    if (!receiverId_.isEmpty()) {
      output.writeBytes(2, receiverId_);
    }
    if (!getMessageTypeBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, messageType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!content_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, content_);
    }
    if (!receiverId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, receiverId_);
    }
    if (!getMessageTypeBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, messageType_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof sawtooth.sdk.protobuf.ConsensusSendToRequest)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.ConsensusSendToRequest other = (sawtooth.sdk.protobuf.ConsensusSendToRequest) obj;

    boolean result = true;
    result = result && getContent()
        .equals(other.getContent());
    result = result && getMessageType()
        .equals(other.getMessageType());
    result = result && getReceiverId()
        .equals(other.getReceiverId());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CONTENT_FIELD_NUMBER;
    hash = (53 * hash) + getContent().hashCode();
    hash = (37 * hash) + MESSAGE_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getMessageType().hashCode();
    hash = (37 * hash) + RECEIVER_ID_FIELD_NUMBER;
    hash = (53 * hash) + getReceiverId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusSendToRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(sawtooth.sdk.protobuf.ConsensusSendToRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Send a consensus message to a specific, connected peer
   * </pre>
   *
   * Protobuf type {@code ConsensusSendToRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ConsensusSendToRequest)
      sawtooth.sdk.protobuf.ConsensusSendToRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusSendToRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusSendToRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.ConsensusSendToRequest.class, sawtooth.sdk.protobuf.ConsensusSendToRequest.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.ConsensusSendToRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      content_ = com.google.protobuf.ByteString.EMPTY;

      messageType_ = "";

      receiverId_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusSendToRequest_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusSendToRequest getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.ConsensusSendToRequest.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusSendToRequest build() {
      sawtooth.sdk.protobuf.ConsensusSendToRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusSendToRequest buildPartial() {
      sawtooth.sdk.protobuf.ConsensusSendToRequest result = new sawtooth.sdk.protobuf.ConsensusSendToRequest(this);
      result.content_ = content_;
      result.messageType_ = messageType_;
      result.receiverId_ = receiverId_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof sawtooth.sdk.protobuf.ConsensusSendToRequest) {
        return mergeFrom((sawtooth.sdk.protobuf.ConsensusSendToRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.ConsensusSendToRequest other) {
      if (other == sawtooth.sdk.protobuf.ConsensusSendToRequest.getDefaultInstance()) return this;
      if (other.getContent() != com.google.protobuf.ByteString.EMPTY) {
        setContent(other.getContent());
      }
      if (!other.getMessageType().isEmpty()) {
        messageType_ = other.messageType_;
        onChanged();
      }
      if (other.getReceiverId() != com.google.protobuf.ByteString.EMPTY) {
        setReceiverId(other.getReceiverId());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      sawtooth.sdk.protobuf.ConsensusSendToRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.ConsensusSendToRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString content_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Payload to send to peer
     * NOTE: This payload will be wrapped up in a ConsensusPeerMessage struct,
     * which includes computing its SHA-512 digest, inserting this engine's
     * registration info, and the validator's public key, and signing everything
     * with the validator's private key.
     * </pre>
     *
     * <code>bytes content = 1;</code>
     */
    public com.google.protobuf.ByteString getContent() {
      return content_;
    }
    /**
     * <pre>
     * Payload to send to peer
     * NOTE: This payload will be wrapped up in a ConsensusPeerMessage struct,
     * which includes computing its SHA-512 digest, inserting this engine's
     * registration info, and the validator's public key, and signing everything
     * with the validator's private key.
     * </pre>
     *
     * <code>bytes content = 1;</code>
     */
    public Builder setContent(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      content_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Payload to send to peer
     * NOTE: This payload will be wrapped up in a ConsensusPeerMessage struct,
     * which includes computing its SHA-512 digest, inserting this engine's
     * registration info, and the validator's public key, and signing everything
     * with the validator's private key.
     * </pre>
     *
     * <code>bytes content = 1;</code>
     */
    public Builder clearContent() {
      
      content_ = getDefaultInstance().getContent();
      onChanged();
      return this;
    }

    private java.lang.Object messageType_ = "";
    /**
     * <code>string message_type = 3;</code>
     */
    public java.lang.String getMessageType() {
      java.lang.Object ref = messageType_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        messageType_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string message_type = 3;</code>
     */
    public com.google.protobuf.ByteString
        getMessageTypeBytes() {
      java.lang.Object ref = messageType_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        messageType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string message_type = 3;</code>
     */
    public Builder setMessageType(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      messageType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string message_type = 3;</code>
     */
    public Builder clearMessageType() {
      
      messageType_ = getDefaultInstance().getMessageType();
      onChanged();
      return this;
    }
    /**
     * <code>string message_type = 3;</code>
     */
    public Builder setMessageTypeBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      messageType_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString receiverId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * Peer that this message is destined for
     * </pre>
     *
     * <code>bytes receiver_id = 2;</code>
     */
    public com.google.protobuf.ByteString getReceiverId() {
      return receiverId_;
    }
    /**
     * <pre>
     * Peer that this message is destined for
     * </pre>
     *
     * <code>bytes receiver_id = 2;</code>
     */
    public Builder setReceiverId(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      receiverId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Peer that this message is destined for
     * </pre>
     *
     * <code>bytes receiver_id = 2;</code>
     */
    public Builder clearReceiverId() {
      
      receiverId_ = getDefaultInstance().getReceiverId();
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ConsensusSendToRequest)
  }

  // @@protoc_insertion_point(class_scope:ConsensusSendToRequest)
  private static final sawtooth.sdk.protobuf.ConsensusSendToRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.ConsensusSendToRequest();
  }

  public static sawtooth.sdk.protobuf.ConsensusSendToRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConsensusSendToRequest>
      PARSER = new com.google.protobuf.AbstractParser<ConsensusSendToRequest>() {
    @java.lang.Override
    public ConsensusSendToRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ConsensusSendToRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ConsensusSendToRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConsensusSendToRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.ConsensusSendToRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

