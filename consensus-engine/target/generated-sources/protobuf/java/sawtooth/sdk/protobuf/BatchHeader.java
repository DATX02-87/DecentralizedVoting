// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: batch.proto

package sawtooth.sdk.protobuf;

/**
 * Protobuf type {@code BatchHeader}
 */
public  final class BatchHeader extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:BatchHeader)
    BatchHeaderOrBuilder {
private static final long serialVersionUID = 0L;
  // Use BatchHeader.newBuilder() to construct.
  private BatchHeader(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private BatchHeader() {
    signerPublicKey_ = "";
    transactionIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private BatchHeader(
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
            java.lang.String s = input.readStringRequireUtf8();

            signerPublicKey_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              transactionIds_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000002;
            }
            transactionIds_.add(s);
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
      if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
        transactionIds_ = transactionIds_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_BatchHeader_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_BatchHeader_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.BatchHeader.class, sawtooth.sdk.protobuf.BatchHeader.Builder.class);
  }

  private int bitField0_;
  public static final int SIGNER_PUBLIC_KEY_FIELD_NUMBER = 1;
  private volatile java.lang.Object signerPublicKey_;
  /**
   * <pre>
   * Public key for the client that signed the BatchHeader
   * </pre>
   *
   * <code>string signer_public_key = 1;</code>
   */
  public java.lang.String getSignerPublicKey() {
    java.lang.Object ref = signerPublicKey_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      signerPublicKey_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Public key for the client that signed the BatchHeader
   * </pre>
   *
   * <code>string signer_public_key = 1;</code>
   */
  public com.google.protobuf.ByteString
      getSignerPublicKeyBytes() {
    java.lang.Object ref = signerPublicKey_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      signerPublicKey_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TRANSACTION_IDS_FIELD_NUMBER = 2;
  private com.google.protobuf.LazyStringList transactionIds_;
  /**
   * <pre>
   * List of transaction.header_signatures that match the order of
   * transactions required for the batch
   * </pre>
   *
   * <code>repeated string transaction_ids = 2;</code>
   */
  public com.google.protobuf.ProtocolStringList
      getTransactionIdsList() {
    return transactionIds_;
  }
  /**
   * <pre>
   * List of transaction.header_signatures that match the order of
   * transactions required for the batch
   * </pre>
   *
   * <code>repeated string transaction_ids = 2;</code>
   */
  public int getTransactionIdsCount() {
    return transactionIds_.size();
  }
  /**
   * <pre>
   * List of transaction.header_signatures that match the order of
   * transactions required for the batch
   * </pre>
   *
   * <code>repeated string transaction_ids = 2;</code>
   */
  public java.lang.String getTransactionIds(int index) {
    return transactionIds_.get(index);
  }
  /**
   * <pre>
   * List of transaction.header_signatures that match the order of
   * transactions required for the batch
   * </pre>
   *
   * <code>repeated string transaction_ids = 2;</code>
   */
  public com.google.protobuf.ByteString
      getTransactionIdsBytes(int index) {
    return transactionIds_.getByteString(index);
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
    if (!getSignerPublicKeyBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, signerPublicKey_);
    }
    for (int i = 0; i < transactionIds_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, transactionIds_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getSignerPublicKeyBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, signerPublicKey_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < transactionIds_.size(); i++) {
        dataSize += computeStringSizeNoTag(transactionIds_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getTransactionIdsList().size();
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
    if (!(obj instanceof sawtooth.sdk.protobuf.BatchHeader)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.BatchHeader other = (sawtooth.sdk.protobuf.BatchHeader) obj;

    boolean result = true;
    result = result && getSignerPublicKey()
        .equals(other.getSignerPublicKey());
    result = result && getTransactionIdsList()
        .equals(other.getTransactionIdsList());
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
    hash = (37 * hash) + SIGNER_PUBLIC_KEY_FIELD_NUMBER;
    hash = (53 * hash) + getSignerPublicKey().hashCode();
    if (getTransactionIdsCount() > 0) {
      hash = (37 * hash) + TRANSACTION_IDS_FIELD_NUMBER;
      hash = (53 * hash) + getTransactionIdsList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.BatchHeader parseFrom(
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
  public static Builder newBuilder(sawtooth.sdk.protobuf.BatchHeader prototype) {
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
   * Protobuf type {@code BatchHeader}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:BatchHeader)
      sawtooth.sdk.protobuf.BatchHeaderOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_BatchHeader_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_BatchHeader_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.BatchHeader.class, sawtooth.sdk.protobuf.BatchHeader.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.BatchHeader.newBuilder()
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
      signerPublicKey_ = "";

      transactionIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.BatchOuterClass.internal_static_BatchHeader_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.BatchHeader getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.BatchHeader.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.BatchHeader build() {
      sawtooth.sdk.protobuf.BatchHeader result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.BatchHeader buildPartial() {
      sawtooth.sdk.protobuf.BatchHeader result = new sawtooth.sdk.protobuf.BatchHeader(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.signerPublicKey_ = signerPublicKey_;
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        transactionIds_ = transactionIds_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000002);
      }
      result.transactionIds_ = transactionIds_;
      result.bitField0_ = to_bitField0_;
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
      if (other instanceof sawtooth.sdk.protobuf.BatchHeader) {
        return mergeFrom((sawtooth.sdk.protobuf.BatchHeader)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.BatchHeader other) {
      if (other == sawtooth.sdk.protobuf.BatchHeader.getDefaultInstance()) return this;
      if (!other.getSignerPublicKey().isEmpty()) {
        signerPublicKey_ = other.signerPublicKey_;
        onChanged();
      }
      if (!other.transactionIds_.isEmpty()) {
        if (transactionIds_.isEmpty()) {
          transactionIds_ = other.transactionIds_;
          bitField0_ = (bitField0_ & ~0x00000002);
        } else {
          ensureTransactionIdsIsMutable();
          transactionIds_.addAll(other.transactionIds_);
        }
        onChanged();
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
      sawtooth.sdk.protobuf.BatchHeader parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.BatchHeader) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object signerPublicKey_ = "";
    /**
     * <pre>
     * Public key for the client that signed the BatchHeader
     * </pre>
     *
     * <code>string signer_public_key = 1;</code>
     */
    public java.lang.String getSignerPublicKey() {
      java.lang.Object ref = signerPublicKey_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        signerPublicKey_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Public key for the client that signed the BatchHeader
     * </pre>
     *
     * <code>string signer_public_key = 1;</code>
     */
    public com.google.protobuf.ByteString
        getSignerPublicKeyBytes() {
      java.lang.Object ref = signerPublicKey_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        signerPublicKey_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Public key for the client that signed the BatchHeader
     * </pre>
     *
     * <code>string signer_public_key = 1;</code>
     */
    public Builder setSignerPublicKey(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      signerPublicKey_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Public key for the client that signed the BatchHeader
     * </pre>
     *
     * <code>string signer_public_key = 1;</code>
     */
    public Builder clearSignerPublicKey() {
      
      signerPublicKey_ = getDefaultInstance().getSignerPublicKey();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Public key for the client that signed the BatchHeader
     * </pre>
     *
     * <code>string signer_public_key = 1;</code>
     */
    public Builder setSignerPublicKeyBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      signerPublicKey_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList transactionIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureTransactionIdsIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        transactionIds_ = new com.google.protobuf.LazyStringArrayList(transactionIds_);
        bitField0_ |= 0x00000002;
       }
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getTransactionIdsList() {
      return transactionIds_.getUnmodifiableView();
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public int getTransactionIdsCount() {
      return transactionIds_.size();
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public java.lang.String getTransactionIds(int index) {
      return transactionIds_.get(index);
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public com.google.protobuf.ByteString
        getTransactionIdsBytes(int index) {
      return transactionIds_.getByteString(index);
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public Builder setTransactionIds(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureTransactionIdsIsMutable();
      transactionIds_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public Builder addTransactionIds(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureTransactionIdsIsMutable();
      transactionIds_.add(value);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public Builder addAllTransactionIds(
        java.lang.Iterable<java.lang.String> values) {
      ensureTransactionIdsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, transactionIds_);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public Builder clearTransactionIds() {
      transactionIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * List of transaction.header_signatures that match the order of
     * transactions required for the batch
     * </pre>
     *
     * <code>repeated string transaction_ids = 2;</code>
     */
    public Builder addTransactionIdsBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureTransactionIdsIsMutable();
      transactionIds_.add(value);
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


    // @@protoc_insertion_point(builder_scope:BatchHeader)
  }

  // @@protoc_insertion_point(class_scope:BatchHeader)
  private static final sawtooth.sdk.protobuf.BatchHeader DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.BatchHeader();
  }

  public static sawtooth.sdk.protobuf.BatchHeader getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<BatchHeader>
      PARSER = new com.google.protobuf.AbstractParser<BatchHeader>() {
    @java.lang.Override
    public BatchHeader parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new BatchHeader(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<BatchHeader> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<BatchHeader> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.BatchHeader getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
