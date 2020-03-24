// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pbft_message.proto

package pbft.sdk.protobuf;

/**
 * <pre>
 * A message sent by the new primary to signify that the new view should be
 * started
 * </pre>
 *
 * Protobuf type {@code PbftNewView}
 */
public  final class PbftNewView extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:PbftNewView)
    PbftNewViewOrBuilder {
private static final long serialVersionUID = 0L;
  // Use PbftNewView.newBuilder() to construct.
  private PbftNewView(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PbftNewView() {
    viewChanges_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private PbftNewView(
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
            pbft.sdk.protobuf.PbftMessageInfo.Builder subBuilder = null;
            if (info_ != null) {
              subBuilder = info_.toBuilder();
            }
            info_ = input.readMessage(pbft.sdk.protobuf.PbftMessageInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(info_);
              info_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
              viewChanges_ = new java.util.ArrayList<pbft.sdk.protobuf.PbftSignedVote>();
              mutable_bitField0_ |= 0x00000002;
            }
            viewChanges_.add(
                input.readMessage(pbft.sdk.protobuf.PbftSignedVote.parser(), extensionRegistry));
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
        viewChanges_ = java.util.Collections.unmodifiableList(viewChanges_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return pbft.sdk.protobuf.PbftMessageOuterClass.internal_static_PbftNewView_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return pbft.sdk.protobuf.PbftMessageOuterClass.internal_static_PbftNewView_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            pbft.sdk.protobuf.PbftNewView.class, pbft.sdk.protobuf.PbftNewView.Builder.class);
  }

  private int bitField0_;
  public static final int INFO_FIELD_NUMBER = 1;
  private pbft.sdk.protobuf.PbftMessageInfo info_;
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.PbftMessageInfo info = 1;</code>
   */
  public boolean hasInfo() {
    return info_ != null;
  }
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.PbftMessageInfo info = 1;</code>
   */
  public pbft.sdk.protobuf.PbftMessageInfo getInfo() {
    return info_ == null ? pbft.sdk.protobuf.PbftMessageInfo.getDefaultInstance() : info_;
  }
  /**
   * <pre>
   * Message information
   * </pre>
   *
   * <code>.PbftMessageInfo info = 1;</code>
   */
  public pbft.sdk.protobuf.PbftMessageInfoOrBuilder getInfoOrBuilder() {
    return getInfo();
  }

  public static final int VIEW_CHANGES_FIELD_NUMBER = 2;
  private java.util.List<pbft.sdk.protobuf.PbftSignedVote> viewChanges_;
  /**
   * <pre>
   * A list of ViewChange messages to prove this view change (must contain at
   * least 2f messages)
   * </pre>
   *
   * <code>repeated .PbftSignedVote view_changes = 2;</code>
   */
  public java.util.List<pbft.sdk.protobuf.PbftSignedVote> getViewChangesList() {
    return viewChanges_;
  }
  /**
   * <pre>
   * A list of ViewChange messages to prove this view change (must contain at
   * least 2f messages)
   * </pre>
   *
   * <code>repeated .PbftSignedVote view_changes = 2;</code>
   */
  public java.util.List<? extends pbft.sdk.protobuf.PbftSignedVoteOrBuilder> 
      getViewChangesOrBuilderList() {
    return viewChanges_;
  }
  /**
   * <pre>
   * A list of ViewChange messages to prove this view change (must contain at
   * least 2f messages)
   * </pre>
   *
   * <code>repeated .PbftSignedVote view_changes = 2;</code>
   */
  public int getViewChangesCount() {
    return viewChanges_.size();
  }
  /**
   * <pre>
   * A list of ViewChange messages to prove this view change (must contain at
   * least 2f messages)
   * </pre>
   *
   * <code>repeated .PbftSignedVote view_changes = 2;</code>
   */
  public pbft.sdk.protobuf.PbftSignedVote getViewChanges(int index) {
    return viewChanges_.get(index);
  }
  /**
   * <pre>
   * A list of ViewChange messages to prove this view change (must contain at
   * least 2f messages)
   * </pre>
   *
   * <code>repeated .PbftSignedVote view_changes = 2;</code>
   */
  public pbft.sdk.protobuf.PbftSignedVoteOrBuilder getViewChangesOrBuilder(
      int index) {
    return viewChanges_.get(index);
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
    if (info_ != null) {
      output.writeMessage(1, getInfo());
    }
    for (int i = 0; i < viewChanges_.size(); i++) {
      output.writeMessage(2, viewChanges_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (info_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getInfo());
    }
    for (int i = 0; i < viewChanges_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, viewChanges_.get(i));
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
    if (!(obj instanceof pbft.sdk.protobuf.PbftNewView)) {
      return super.equals(obj);
    }
    pbft.sdk.protobuf.PbftNewView other = (pbft.sdk.protobuf.PbftNewView) obj;

    boolean result = true;
    result = result && (hasInfo() == other.hasInfo());
    if (hasInfo()) {
      result = result && getInfo()
          .equals(other.getInfo());
    }
    result = result && getViewChangesList()
        .equals(other.getViewChangesList());
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
    if (hasInfo()) {
      hash = (37 * hash) + INFO_FIELD_NUMBER;
      hash = (53 * hash) + getInfo().hashCode();
    }
    if (getViewChangesCount() > 0) {
      hash = (37 * hash) + VIEW_CHANGES_FIELD_NUMBER;
      hash = (53 * hash) + getViewChangesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static pbft.sdk.protobuf.PbftNewView parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static pbft.sdk.protobuf.PbftNewView parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pbft.sdk.protobuf.PbftNewView parseFrom(
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
  public static Builder newBuilder(pbft.sdk.protobuf.PbftNewView prototype) {
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
   * A message sent by the new primary to signify that the new view should be
   * started
   * </pre>
   *
   * Protobuf type {@code PbftNewView}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:PbftNewView)
      pbft.sdk.protobuf.PbftNewViewOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return pbft.sdk.protobuf.PbftMessageOuterClass.internal_static_PbftNewView_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return pbft.sdk.protobuf.PbftMessageOuterClass.internal_static_PbftNewView_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              pbft.sdk.protobuf.PbftNewView.class, pbft.sdk.protobuf.PbftNewView.Builder.class);
    }

    // Construct using pbft.sdk.protobuf.PbftNewView.newBuilder()
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
        getViewChangesFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (infoBuilder_ == null) {
        info_ = null;
      } else {
        info_ = null;
        infoBuilder_ = null;
      }
      if (viewChangesBuilder_ == null) {
        viewChanges_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
      } else {
        viewChangesBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return pbft.sdk.protobuf.PbftMessageOuterClass.internal_static_PbftNewView_descriptor;
    }

    @java.lang.Override
    public pbft.sdk.protobuf.PbftNewView getDefaultInstanceForType() {
      return pbft.sdk.protobuf.PbftNewView.getDefaultInstance();
    }

    @java.lang.Override
    public pbft.sdk.protobuf.PbftNewView build() {
      pbft.sdk.protobuf.PbftNewView result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public pbft.sdk.protobuf.PbftNewView buildPartial() {
      pbft.sdk.protobuf.PbftNewView result = new pbft.sdk.protobuf.PbftNewView(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (infoBuilder_ == null) {
        result.info_ = info_;
      } else {
        result.info_ = infoBuilder_.build();
      }
      if (viewChangesBuilder_ == null) {
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          viewChanges_ = java.util.Collections.unmodifiableList(viewChanges_);
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.viewChanges_ = viewChanges_;
      } else {
        result.viewChanges_ = viewChangesBuilder_.build();
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
      if (other instanceof pbft.sdk.protobuf.PbftNewView) {
        return mergeFrom((pbft.sdk.protobuf.PbftNewView)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(pbft.sdk.protobuf.PbftNewView other) {
      if (other == pbft.sdk.protobuf.PbftNewView.getDefaultInstance()) return this;
      if (other.hasInfo()) {
        mergeInfo(other.getInfo());
      }
      if (viewChangesBuilder_ == null) {
        if (!other.viewChanges_.isEmpty()) {
          if (viewChanges_.isEmpty()) {
            viewChanges_ = other.viewChanges_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureViewChangesIsMutable();
            viewChanges_.addAll(other.viewChanges_);
          }
          onChanged();
        }
      } else {
        if (!other.viewChanges_.isEmpty()) {
          if (viewChangesBuilder_.isEmpty()) {
            viewChangesBuilder_.dispose();
            viewChangesBuilder_ = null;
            viewChanges_ = other.viewChanges_;
            bitField0_ = (bitField0_ & ~0x00000002);
            viewChangesBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getViewChangesFieldBuilder() : null;
          } else {
            viewChangesBuilder_.addAllMessages(other.viewChanges_);
          }
        }
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
      pbft.sdk.protobuf.PbftNewView parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (pbft.sdk.protobuf.PbftNewView) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private pbft.sdk.protobuf.PbftMessageInfo info_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        pbft.sdk.protobuf.PbftMessageInfo, pbft.sdk.protobuf.PbftMessageInfo.Builder, pbft.sdk.protobuf.PbftMessageInfoOrBuilder> infoBuilder_;
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public boolean hasInfo() {
      return infoBuilder_ != null || info_ != null;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public pbft.sdk.protobuf.PbftMessageInfo getInfo() {
      if (infoBuilder_ == null) {
        return info_ == null ? pbft.sdk.protobuf.PbftMessageInfo.getDefaultInstance() : info_;
      } else {
        return infoBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public Builder setInfo(pbft.sdk.protobuf.PbftMessageInfo value) {
      if (infoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        info_ = value;
        onChanged();
      } else {
        infoBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public Builder setInfo(
        pbft.sdk.protobuf.PbftMessageInfo.Builder builderForValue) {
      if (infoBuilder_ == null) {
        info_ = builderForValue.build();
        onChanged();
      } else {
        infoBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public Builder mergeInfo(pbft.sdk.protobuf.PbftMessageInfo value) {
      if (infoBuilder_ == null) {
        if (info_ != null) {
          info_ =
            pbft.sdk.protobuf.PbftMessageInfo.newBuilder(info_).mergeFrom(value).buildPartial();
        } else {
          info_ = value;
        }
        onChanged();
      } else {
        infoBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public Builder clearInfo() {
      if (infoBuilder_ == null) {
        info_ = null;
        onChanged();
      } else {
        info_ = null;
        infoBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public pbft.sdk.protobuf.PbftMessageInfo.Builder getInfoBuilder() {
      
      onChanged();
      return getInfoFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    public pbft.sdk.protobuf.PbftMessageInfoOrBuilder getInfoOrBuilder() {
      if (infoBuilder_ != null) {
        return infoBuilder_.getMessageOrBuilder();
      } else {
        return info_ == null ?
            pbft.sdk.protobuf.PbftMessageInfo.getDefaultInstance() : info_;
      }
    }
    /**
     * <pre>
     * Message information
     * </pre>
     *
     * <code>.PbftMessageInfo info = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        pbft.sdk.protobuf.PbftMessageInfo, pbft.sdk.protobuf.PbftMessageInfo.Builder, pbft.sdk.protobuf.PbftMessageInfoOrBuilder> 
        getInfoFieldBuilder() {
      if (infoBuilder_ == null) {
        infoBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            pbft.sdk.protobuf.PbftMessageInfo, pbft.sdk.protobuf.PbftMessageInfo.Builder, pbft.sdk.protobuf.PbftMessageInfoOrBuilder>(
                getInfo(),
                getParentForChildren(),
                isClean());
        info_ = null;
      }
      return infoBuilder_;
    }

    private java.util.List<pbft.sdk.protobuf.PbftSignedVote> viewChanges_ =
      java.util.Collections.emptyList();
    private void ensureViewChangesIsMutable() {
      if (!((bitField0_ & 0x00000002) == 0x00000002)) {
        viewChanges_ = new java.util.ArrayList<pbft.sdk.protobuf.PbftSignedVote>(viewChanges_);
        bitField0_ |= 0x00000002;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        pbft.sdk.protobuf.PbftSignedVote, pbft.sdk.protobuf.PbftSignedVote.Builder, pbft.sdk.protobuf.PbftSignedVoteOrBuilder> viewChangesBuilder_;

    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public java.util.List<pbft.sdk.protobuf.PbftSignedVote> getViewChangesList() {
      if (viewChangesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(viewChanges_);
      } else {
        return viewChangesBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public int getViewChangesCount() {
      if (viewChangesBuilder_ == null) {
        return viewChanges_.size();
      } else {
        return viewChangesBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public pbft.sdk.protobuf.PbftSignedVote getViewChanges(int index) {
      if (viewChangesBuilder_ == null) {
        return viewChanges_.get(index);
      } else {
        return viewChangesBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder setViewChanges(
        int index, pbft.sdk.protobuf.PbftSignedVote value) {
      if (viewChangesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureViewChangesIsMutable();
        viewChanges_.set(index, value);
        onChanged();
      } else {
        viewChangesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder setViewChanges(
        int index, pbft.sdk.protobuf.PbftSignedVote.Builder builderForValue) {
      if (viewChangesBuilder_ == null) {
        ensureViewChangesIsMutable();
        viewChanges_.set(index, builderForValue.build());
        onChanged();
      } else {
        viewChangesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder addViewChanges(pbft.sdk.protobuf.PbftSignedVote value) {
      if (viewChangesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureViewChangesIsMutable();
        viewChanges_.add(value);
        onChanged();
      } else {
        viewChangesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder addViewChanges(
        int index, pbft.sdk.protobuf.PbftSignedVote value) {
      if (viewChangesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureViewChangesIsMutable();
        viewChanges_.add(index, value);
        onChanged();
      } else {
        viewChangesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder addViewChanges(
        pbft.sdk.protobuf.PbftSignedVote.Builder builderForValue) {
      if (viewChangesBuilder_ == null) {
        ensureViewChangesIsMutable();
        viewChanges_.add(builderForValue.build());
        onChanged();
      } else {
        viewChangesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder addViewChanges(
        int index, pbft.sdk.protobuf.PbftSignedVote.Builder builderForValue) {
      if (viewChangesBuilder_ == null) {
        ensureViewChangesIsMutable();
        viewChanges_.add(index, builderForValue.build());
        onChanged();
      } else {
        viewChangesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder addAllViewChanges(
        java.lang.Iterable<? extends pbft.sdk.protobuf.PbftSignedVote> values) {
      if (viewChangesBuilder_ == null) {
        ensureViewChangesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, viewChanges_);
        onChanged();
      } else {
        viewChangesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder clearViewChanges() {
      if (viewChangesBuilder_ == null) {
        viewChanges_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
      } else {
        viewChangesBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public Builder removeViewChanges(int index) {
      if (viewChangesBuilder_ == null) {
        ensureViewChangesIsMutable();
        viewChanges_.remove(index);
        onChanged();
      } else {
        viewChangesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public pbft.sdk.protobuf.PbftSignedVote.Builder getViewChangesBuilder(
        int index) {
      return getViewChangesFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public pbft.sdk.protobuf.PbftSignedVoteOrBuilder getViewChangesOrBuilder(
        int index) {
      if (viewChangesBuilder_ == null) {
        return viewChanges_.get(index);  } else {
        return viewChangesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public java.util.List<? extends pbft.sdk.protobuf.PbftSignedVoteOrBuilder> 
         getViewChangesOrBuilderList() {
      if (viewChangesBuilder_ != null) {
        return viewChangesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(viewChanges_);
      }
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public pbft.sdk.protobuf.PbftSignedVote.Builder addViewChangesBuilder() {
      return getViewChangesFieldBuilder().addBuilder(
          pbft.sdk.protobuf.PbftSignedVote.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public pbft.sdk.protobuf.PbftSignedVote.Builder addViewChangesBuilder(
        int index) {
      return getViewChangesFieldBuilder().addBuilder(
          index, pbft.sdk.protobuf.PbftSignedVote.getDefaultInstance());
    }
    /**
     * <pre>
     * A list of ViewChange messages to prove this view change (must contain at
     * least 2f messages)
     * </pre>
     *
     * <code>repeated .PbftSignedVote view_changes = 2;</code>
     */
    public java.util.List<pbft.sdk.protobuf.PbftSignedVote.Builder> 
         getViewChangesBuilderList() {
      return getViewChangesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        pbft.sdk.protobuf.PbftSignedVote, pbft.sdk.protobuf.PbftSignedVote.Builder, pbft.sdk.protobuf.PbftSignedVoteOrBuilder> 
        getViewChangesFieldBuilder() {
      if (viewChangesBuilder_ == null) {
        viewChangesBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            pbft.sdk.protobuf.PbftSignedVote, pbft.sdk.protobuf.PbftSignedVote.Builder, pbft.sdk.protobuf.PbftSignedVoteOrBuilder>(
                viewChanges_,
                ((bitField0_ & 0x00000002) == 0x00000002),
                getParentForChildren(),
                isClean());
        viewChanges_ = null;
      }
      return viewChangesBuilder_;
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


    // @@protoc_insertion_point(builder_scope:PbftNewView)
  }

  // @@protoc_insertion_point(class_scope:PbftNewView)
  private static final pbft.sdk.protobuf.PbftNewView DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new pbft.sdk.protobuf.PbftNewView();
  }

  public static pbft.sdk.protobuf.PbftNewView getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PbftNewView>
      PARSER = new com.google.protobuf.AbstractParser<PbftNewView>() {
    @java.lang.Override
    public PbftNewView parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new PbftNewView(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PbftNewView> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PbftNewView> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public pbft.sdk.protobuf.PbftNewView getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
