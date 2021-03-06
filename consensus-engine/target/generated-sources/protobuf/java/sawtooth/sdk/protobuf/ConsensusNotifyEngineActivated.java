// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consensus.proto

package sawtooth.sdk.protobuf;

/**
 * <pre>
 * The engine has been activated
 * </pre>
 *
 * Protobuf type {@code ConsensusNotifyEngineActivated}
 */
public  final class ConsensusNotifyEngineActivated extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ConsensusNotifyEngineActivated)
    ConsensusNotifyEngineActivatedOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConsensusNotifyEngineActivated.newBuilder() to construct.
  private ConsensusNotifyEngineActivated(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConsensusNotifyEngineActivated() {
    peers_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ConsensusNotifyEngineActivated(
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
            sawtooth.sdk.protobuf.ConsensusBlock.Builder subBuilder = null;
            if (chainHead_ != null) {
              subBuilder = chainHead_.toBuilder();
            }
            chainHead_ = input.readMessage(sawtooth.sdk.protobuf.ConsensusBlock.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(chainHead_);
              chainHead_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              peers_ = new java.util.ArrayList<sawtooth.sdk.protobuf.ConsensusPeerInfo>();
              mutable_bitField0_ |= 0x00000002;
            }
            peers_.add(
                input.readMessage(sawtooth.sdk.protobuf.ConsensusPeerInfo.parser(), extensionRegistry));
            break;
          }
          case 26: {
            sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder subBuilder = null;
            if (localPeerInfo_ != null) {
              subBuilder = localPeerInfo_.toBuilder();
            }
            localPeerInfo_ = input.readMessage(sawtooth.sdk.protobuf.ConsensusPeerInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(localPeerInfo_);
              localPeerInfo_ = subBuilder.buildPartial();
            }

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
        peers_ = java.util.Collections.unmodifiableList(peers_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusNotifyEngineActivated_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusNotifyEngineActivated_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.class, sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.Builder.class);
  }

  private int bitField0_;
  public static final int CHAIN_HEAD_FIELD_NUMBER = 1;
  private sawtooth.sdk.protobuf.ConsensusBlock chainHead_;
  /**
   * <pre>
   * Startup Info
   * </pre>
   *
   * <code>.ConsensusBlock chain_head = 1;</code>
   */
  public boolean hasChainHead() {
    return chainHead_ != null;
  }
  /**
   * <pre>
   * Startup Info
   * </pre>
   *
   * <code>.ConsensusBlock chain_head = 1;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusBlock getChainHead() {
    return chainHead_ == null ? sawtooth.sdk.protobuf.ConsensusBlock.getDefaultInstance() : chainHead_;
  }
  /**
   * <pre>
   * Startup Info
   * </pre>
   *
   * <code>.ConsensusBlock chain_head = 1;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusBlockOrBuilder getChainHeadOrBuilder() {
    return getChainHead();
  }

  public static final int PEERS_FIELD_NUMBER = 2;
  private java.util.List<sawtooth.sdk.protobuf.ConsensusPeerInfo> peers_;
  /**
   * <code>repeated .ConsensusPeerInfo peers = 2;</code>
   */
  public java.util.List<sawtooth.sdk.protobuf.ConsensusPeerInfo> getPeersList() {
    return peers_;
  }
  /**
   * <code>repeated .ConsensusPeerInfo peers = 2;</code>
   */
  public java.util.List<? extends sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> 
      getPeersOrBuilderList() {
    return peers_;
  }
  /**
   * <code>repeated .ConsensusPeerInfo peers = 2;</code>
   */
  public int getPeersCount() {
    return peers_.size();
  }
  /**
   * <code>repeated .ConsensusPeerInfo peers = 2;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusPeerInfo getPeers(int index) {
    return peers_.get(index);
  }
  /**
   * <code>repeated .ConsensusPeerInfo peers = 2;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder getPeersOrBuilder(
      int index) {
    return peers_.get(index);
  }

  public static final int LOCAL_PEER_INFO_FIELD_NUMBER = 3;
  private sawtooth.sdk.protobuf.ConsensusPeerInfo localPeerInfo_;
  /**
   * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
   */
  public boolean hasLocalPeerInfo() {
    return localPeerInfo_ != null;
  }
  /**
   * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusPeerInfo getLocalPeerInfo() {
    return localPeerInfo_ == null ? sawtooth.sdk.protobuf.ConsensusPeerInfo.getDefaultInstance() : localPeerInfo_;
  }
  /**
   * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
   */
  public sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder getLocalPeerInfoOrBuilder() {
    return getLocalPeerInfo();
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
    if (chainHead_ != null) {
      output.writeMessage(1, getChainHead());
    }
    for (int i = 0; i < peers_.size(); i++) {
      output.writeMessage(2, peers_.get(i));
    }
    if (localPeerInfo_ != null) {
      output.writeMessage(3, getLocalPeerInfo());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (chainHead_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getChainHead());
    }
    for (int i = 0; i < peers_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, peers_.get(i));
    }
    if (localPeerInfo_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getLocalPeerInfo());
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
    if (!(obj instanceof sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated)) {
      return super.equals(obj);
    }
    sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated other = (sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated) obj;

    boolean result = true;
    result = result && (hasChainHead() == other.hasChainHead());
    if (hasChainHead()) {
      result = result && getChainHead()
          .equals(other.getChainHead());
    }
    result = result && getPeersList()
        .equals(other.getPeersList());
    result = result && (hasLocalPeerInfo() == other.hasLocalPeerInfo());
    if (hasLocalPeerInfo()) {
      result = result && getLocalPeerInfo()
          .equals(other.getLocalPeerInfo());
    }
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
    if (hasChainHead()) {
      hash = (37 * hash) + CHAIN_HEAD_FIELD_NUMBER;
      hash = (53 * hash) + getChainHead().hashCode();
    }
    if (getPeersCount() > 0) {
      hash = (37 * hash) + PEERS_FIELD_NUMBER;
      hash = (53 * hash) + getPeersList().hashCode();
    }
    if (hasLocalPeerInfo()) {
      hash = (37 * hash) + LOCAL_PEER_INFO_FIELD_NUMBER;
      hash = (53 * hash) + getLocalPeerInfo().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parseFrom(
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
  public static Builder newBuilder(sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated prototype) {
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
   * The engine has been activated
   * </pre>
   *
   * Protobuf type {@code ConsensusNotifyEngineActivated}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ConsensusNotifyEngineActivated)
      sawtooth.sdk.protobuf.ConsensusNotifyEngineActivatedOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusNotifyEngineActivated_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusNotifyEngineActivated_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.class, sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.Builder.class);
    }

    // Construct using sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.newBuilder()
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
        getPeersFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (chainHeadBuilder_ == null) {
        chainHead_ = null;
      } else {
        chainHead_ = null;
        chainHeadBuilder_ = null;
      }
      if (peersBuilder_ == null) {
        peers_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        peersBuilder_.clear();
      }
      if (localPeerInfoBuilder_ == null) {
        localPeerInfo_ = null;
      } else {
        localPeerInfo_ = null;
        localPeerInfoBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sawtooth.sdk.protobuf.Consensus.internal_static_ConsensusNotifyEngineActivated_descriptor;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated getDefaultInstanceForType() {
      return sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.getDefaultInstance();
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated build() {
      sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated buildPartial() {
      sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated result = new sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (chainHeadBuilder_ == null) {
        result.chainHead_ = chainHead_;
      } else {
        result.chainHead_ = chainHeadBuilder_.build();
      }
      if (peersBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          peers_ = java.util.Collections.unmodifiableList(peers_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.peers_ = peers_;
      } else {
        result.peers_ = peersBuilder_.build();
      }
      if (localPeerInfoBuilder_ == null) {
        result.localPeerInfo_ = localPeerInfo_;
      } else {
        result.localPeerInfo_ = localPeerInfoBuilder_.build();
      }
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
      if (other instanceof sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated) {
        return mergeFrom((sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated other) {
      if (other == sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated.getDefaultInstance()) return this;
      if (other.hasChainHead()) {
        mergeChainHead(other.getChainHead());
      }
      if (peersBuilder_ == null) {
        if (!other.peers_.isEmpty()) {
          if (peers_.isEmpty()) {
            peers_ = other.peers_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensurePeersIsMutable();
            peers_.addAll(other.peers_);
          }
          onChanged();
        }
      } else {
        if (!other.peers_.isEmpty()) {
          if (peersBuilder_.isEmpty()) {
            peersBuilder_.dispose();
            peersBuilder_ = null;
            peers_ = other.peers_;
            bitField0_ = (bitField0_ & ~0x00000002);
            peersBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getPeersFieldBuilder() : null;
          } else {
            peersBuilder_.addAllMessages(other.peers_);
          }
        }
      }
      if (other.hasLocalPeerInfo()) {
        mergeLocalPeerInfo(other.getLocalPeerInfo());
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
      sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private sawtooth.sdk.protobuf.ConsensusBlock chainHead_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> chainHeadBuilder_;
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public boolean hasChainHead() {
      return chainHeadBuilder_ != null || chainHead_ != null;
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock getChainHead() {
      if (chainHeadBuilder_ == null) {
        return chainHead_ == null ? sawtooth.sdk.protobuf.ConsensusBlock.getDefaultInstance() : chainHead_;
      } else {
        return chainHeadBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public Builder setChainHead(sawtooth.sdk.protobuf.ConsensusBlock value) {
      if (chainHeadBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        chainHead_ = value;
        onChanged();
      } else {
        chainHeadBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public Builder setChainHead(
        sawtooth.sdk.protobuf.ConsensusBlock.Builder builderForValue) {
      if (chainHeadBuilder_ == null) {
        chainHead_ = builderForValue.build();
        onChanged();
      } else {
        chainHeadBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public Builder mergeChainHead(sawtooth.sdk.protobuf.ConsensusBlock value) {
      if (chainHeadBuilder_ == null) {
        if (chainHead_ != null) {
          chainHead_ =
            sawtooth.sdk.protobuf.ConsensusBlock.newBuilder(chainHead_).mergeFrom(value).buildPartial();
        } else {
          chainHead_ = value;
        }
        onChanged();
      } else {
        chainHeadBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public Builder clearChainHead() {
      if (chainHeadBuilder_ == null) {
        chainHead_ = null;
        onChanged();
      } else {
        chainHead_ = null;
        chainHeadBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlock.Builder getChainHeadBuilder() {
      
      onChanged();
      return getChainHeadFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusBlockOrBuilder getChainHeadOrBuilder() {
      if (chainHeadBuilder_ != null) {
        return chainHeadBuilder_.getMessageOrBuilder();
      } else {
        return chainHead_ == null ?
            sawtooth.sdk.protobuf.ConsensusBlock.getDefaultInstance() : chainHead_;
      }
    }
    /**
     * <pre>
     * Startup Info
     * </pre>
     *
     * <code>.ConsensusBlock chain_head = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder> 
        getChainHeadFieldBuilder() {
      if (chainHeadBuilder_ == null) {
        chainHeadBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            sawtooth.sdk.protobuf.ConsensusBlock, sawtooth.sdk.protobuf.ConsensusBlock.Builder, sawtooth.sdk.protobuf.ConsensusBlockOrBuilder>(
                getChainHead(),
                getParentForChildren(),
                isClean());
        chainHead_ = null;
      }
      return chainHeadBuilder_;
    }

    private java.util.List<sawtooth.sdk.protobuf.ConsensusPeerInfo> peers_ =
      java.util.Collections.emptyList();
    private void ensurePeersIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        peers_ = new java.util.ArrayList<sawtooth.sdk.protobuf.ConsensusPeerInfo>(peers_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> peersBuilder_;

    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.ConsensusPeerInfo> getPeersList() {
      if (peersBuilder_ == null) {
        return java.util.Collections.unmodifiableList(peers_);
      } else {
        return peersBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public int getPeersCount() {
      if (peersBuilder_ == null) {
        return peers_.size();
      } else {
        return peersBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo getPeers(int index) {
      if (peersBuilder_ == null) {
        return peers_.get(index);
      } else {
        return peersBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder setPeers(
        int index, sawtooth.sdk.protobuf.ConsensusPeerInfo value) {
      if (peersBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeersIsMutable();
        peers_.set(index, value);
        onChanged();
      } else {
        peersBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder setPeers(
        int index, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder builderForValue) {
      if (peersBuilder_ == null) {
        ensurePeersIsMutable();
        peers_.set(index, builderForValue.build());
        onChanged();
      } else {
        peersBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder addPeers(sawtooth.sdk.protobuf.ConsensusPeerInfo value) {
      if (peersBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeersIsMutable();
        peers_.add(value);
        onChanged();
      } else {
        peersBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder addPeers(
        int index, sawtooth.sdk.protobuf.ConsensusPeerInfo value) {
      if (peersBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePeersIsMutable();
        peers_.add(index, value);
        onChanged();
      } else {
        peersBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder addPeers(
        sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder builderForValue) {
      if (peersBuilder_ == null) {
        ensurePeersIsMutable();
        peers_.add(builderForValue.build());
        onChanged();
      } else {
        peersBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder addPeers(
        int index, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder builderForValue) {
      if (peersBuilder_ == null) {
        ensurePeersIsMutable();
        peers_.add(index, builderForValue.build());
        onChanged();
      } else {
        peersBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder addAllPeers(
        java.lang.Iterable<? extends sawtooth.sdk.protobuf.ConsensusPeerInfo> values) {
      if (peersBuilder_ == null) {
        ensurePeersIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, peers_);
        onChanged();
      } else {
        peersBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder clearPeers() {
      if (peersBuilder_ == null) {
        peers_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        peersBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public Builder removePeers(int index) {
      if (peersBuilder_ == null) {
        ensurePeersIsMutable();
        peers_.remove(index);
        onChanged();
      } else {
        peersBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder getPeersBuilder(
        int index) {
      return getPeersFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder getPeersOrBuilder(
        int index) {
      if (peersBuilder_ == null) {
        return peers_.get(index);  } else {
        return peersBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public java.util.List<? extends sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> 
         getPeersOrBuilderList() {
      if (peersBuilder_ != null) {
        return peersBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(peers_);
      }
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder addPeersBuilder() {
      return getPeersFieldBuilder().addBuilder(
          sawtooth.sdk.protobuf.ConsensusPeerInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder addPeersBuilder(
        int index) {
      return getPeersFieldBuilder().addBuilder(
          index, sawtooth.sdk.protobuf.ConsensusPeerInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .ConsensusPeerInfo peers = 2;</code>
     */
    public java.util.List<sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder> 
         getPeersBuilderList() {
      return getPeersFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> 
        getPeersFieldBuilder() {
      if (peersBuilder_ == null) {
        peersBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder>(
                peers_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        peers_ = null;
      }
      return peersBuilder_;
    }

    private sawtooth.sdk.protobuf.ConsensusPeerInfo localPeerInfo_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> localPeerInfoBuilder_;
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public boolean hasLocalPeerInfo() {
      return localPeerInfoBuilder_ != null || localPeerInfo_ != null;
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo getLocalPeerInfo() {
      if (localPeerInfoBuilder_ == null) {
        return localPeerInfo_ == null ? sawtooth.sdk.protobuf.ConsensusPeerInfo.getDefaultInstance() : localPeerInfo_;
      } else {
        return localPeerInfoBuilder_.getMessage();
      }
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public Builder setLocalPeerInfo(sawtooth.sdk.protobuf.ConsensusPeerInfo value) {
      if (localPeerInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        localPeerInfo_ = value;
        onChanged();
      } else {
        localPeerInfoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public Builder setLocalPeerInfo(
        sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder builderForValue) {
      if (localPeerInfoBuilder_ == null) {
        localPeerInfo_ = builderForValue.build();
        onChanged();
      } else {
        localPeerInfoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public Builder mergeLocalPeerInfo(sawtooth.sdk.protobuf.ConsensusPeerInfo value) {
      if (localPeerInfoBuilder_ == null) {
        if (localPeerInfo_ != null) {
          localPeerInfo_ =
            sawtooth.sdk.protobuf.ConsensusPeerInfo.newBuilder(localPeerInfo_).mergeFrom(value).buildPartial();
        } else {
          localPeerInfo_ = value;
        }
        onChanged();
      } else {
        localPeerInfoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public Builder clearLocalPeerInfo() {
      if (localPeerInfoBuilder_ == null) {
        localPeerInfo_ = null;
        onChanged();
      } else {
        localPeerInfo_ = null;
        localPeerInfoBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder getLocalPeerInfoBuilder() {
      
      onChanged();
      return getLocalPeerInfoFieldBuilder().getBuilder();
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    public sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder getLocalPeerInfoOrBuilder() {
      if (localPeerInfoBuilder_ != null) {
        return localPeerInfoBuilder_.getMessageOrBuilder();
      } else {
        return localPeerInfo_ == null ?
            sawtooth.sdk.protobuf.ConsensusPeerInfo.getDefaultInstance() : localPeerInfo_;
      }
    }
    /**
     * <code>.ConsensusPeerInfo local_peer_info = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder> 
        getLocalPeerInfoFieldBuilder() {
      if (localPeerInfoBuilder_ == null) {
        localPeerInfoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            sawtooth.sdk.protobuf.ConsensusPeerInfo, sawtooth.sdk.protobuf.ConsensusPeerInfo.Builder, sawtooth.sdk.protobuf.ConsensusPeerInfoOrBuilder>(
                getLocalPeerInfo(),
                getParentForChildren(),
                isClean());
        localPeerInfo_ = null;
      }
      return localPeerInfoBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ConsensusNotifyEngineActivated)
  }

  // @@protoc_insertion_point(class_scope:ConsensusNotifyEngineActivated)
  private static final sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated();
  }

  public static sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConsensusNotifyEngineActivated>
      PARSER = new com.google.protobuf.AbstractParser<ConsensusNotifyEngineActivated>() {
    @java.lang.Override
    public ConsensusNotifyEngineActivated parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ConsensusNotifyEngineActivated(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ConsensusNotifyEngineActivated> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConsensusNotifyEngineActivated> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sawtooth.sdk.protobuf.ConsensusNotifyEngineActivated getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

