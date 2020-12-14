// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package protobuf;

/**
 * Protobuf enum {@code user_type}
 */
public enum user_type
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>unknown = 0;</code>
   */
  unknown(0),
  /**
   * <code>simple = 1;</code>
   */
  simple(1),
  /**
   * <code>admin = 2;</code>
   */
  admin(2),
  /**
   * <code>super_admin = 3;</code>
   */
  super_admin(3),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>unknown = 0;</code>
   */
  public static final int unknown_VALUE = 0;
  /**
   * <code>simple = 1;</code>
   */
  public static final int simple_VALUE = 1;
  /**
   * <code>admin = 2;</code>
   */
  public static final int admin_VALUE = 2;
  /**
   * <code>super_admin = 3;</code>
   */
  public static final int super_admin_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static user_type valueOf(int value) {
    return forNumber(value);
  }

  public static user_type forNumber(int value) {
    switch (value) {
      case 0: return unknown;
      case 1: return simple;
      case 2: return admin;
      case 3: return super_admin;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<user_type>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      user_type> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<user_type>() {
          public user_type findValueByNumber(int number) {
            return user_type.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return protobuf.User.getDescriptor().getEnumTypes().get(0);
  }

  private static final user_type[] VALUES = values();

  public static user_type valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private user_type(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:user_type)
}

