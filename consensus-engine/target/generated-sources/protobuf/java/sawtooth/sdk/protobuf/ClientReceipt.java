// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: client_receipt.proto

package sawtooth.sdk.protobuf;

public final class ClientReceipt {
  private ClientReceipt() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientReceiptGetRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientReceiptGetRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ClientReceiptGetResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ClientReceiptGetResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024client_receipt.proto\032\031transaction_rece" +
      "ipt.proto\"2\n\027ClientReceiptGetRequest\022\027\n\017" +
      "transaction_ids\030\001 \003(\t\"\314\001\n\030ClientReceiptG" +
      "etResponse\0220\n\006status\030\001 \001(\0162 .ClientRecei" +
      "ptGetResponse.Status\022%\n\010receipts\030\002 \003(\0132\023" +
      ".TransactionReceipt\"W\n\006Status\022\020\n\014STATUS_" +
      "UNSET\020\000\022\006\n\002OK\020\001\022\022\n\016INTERNAL_ERROR\020\002\022\017\n\013N" +
      "O_RESOURCE\020\005\022\016\n\nINVALID_ID\020\010B-\n\025sawtooth" +
      ".sdk.protobufP\001Z\022client_receipt_pb2b\006pro" +
      "to3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          sawtooth.sdk.protobuf.TransactionReceiptOuterClass.getDescriptor(),
        }, assigner);
    internal_static_ClientReceiptGetRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ClientReceiptGetRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientReceiptGetRequest_descriptor,
        new java.lang.String[] { "TransactionIds", });
    internal_static_ClientReceiptGetResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ClientReceiptGetResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ClientReceiptGetResponse_descriptor,
        new java.lang.String[] { "Status", "Receipts", });
    sawtooth.sdk.protobuf.TransactionReceiptOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}