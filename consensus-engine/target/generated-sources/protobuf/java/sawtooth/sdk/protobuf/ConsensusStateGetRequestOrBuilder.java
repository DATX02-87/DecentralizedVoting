// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: consensus.proto

package sawtooth.sdk.protobuf;

public interface ConsensusStateGetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ConsensusStateGetRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes block_id = 1;</code>
   */
  com.google.protobuf.ByteString getBlockId();

  /**
   * <code>repeated string addresses = 2;</code>
   */
  java.util.List<java.lang.String>
      getAddressesList();
  /**
   * <code>repeated string addresses = 2;</code>
   */
  int getAddressesCount();
  /**
   * <code>repeated string addresses = 2;</code>
   */
  java.lang.String getAddresses(int index);
  /**
   * <code>repeated string addresses = 2;</code>
   */
  com.google.protobuf.ByteString
      getAddressesBytes(int index);
}
