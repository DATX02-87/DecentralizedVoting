// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: network.proto

package sawtooth.sdk.protobuf;

public interface GossipMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:GossipMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes content = 1;</code>
   */
  com.google.protobuf.ByteString getContent();

  /**
   * <code>.GossipMessage.ContentType content_type = 2;</code>
   */
  int getContentTypeValue();
  /**
   * <code>.GossipMessage.ContentType content_type = 2;</code>
   */
  sawtooth.sdk.protobuf.GossipMessage.ContentType getContentType();

  /**
   * <code>uint32 time_to_live = 3;</code>
   */
  int getTimeToLive();
}
