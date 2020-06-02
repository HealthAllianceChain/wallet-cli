package org.hac.core.zen.address;

import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hac.api.GrpcAPI.DiversifierMessage;
import org.hac.api.GrpcAPI.IncomingViewingKeyDiversifierMessage;
import org.hac.api.GrpcAPI.IncomingViewingKeyMessage;
import org.hac.api.GrpcAPI.PaymentAddressMessage;
import org.hac.walletserver.WalletApi;

import java.util.Optional;

@Slf4j(topic = "shieldTransaction")
@AllArgsConstructor
public class IncomingViewingKey {

  @Setter
  @Getter
  public byte[] value; // 256

  public Optional<PaymentAddress> address(DiversifierT d) {
    DiversifierMessage.Builder dBuilder = DiversifierMessage.newBuilder();
    dBuilder.setD(ByteString.copyFrom(d.getData()));

    IncomingViewingKeyMessage.Builder incomingBuilder = IncomingViewingKeyMessage.newBuilder();
    incomingBuilder.setIvk(ByteString.copyFrom(value));

    IncomingViewingKeyDiversifierMessage.Builder builder = IncomingViewingKeyDiversifierMessage.newBuilder();
    builder.setD(dBuilder.build());
    builder.setIvk(incomingBuilder.build());
    Optional<PaymentAddressMessage> addressMessage = WalletApi.getZenPaymentAddress(builder.build());
    if (addressMessage.isPresent()) {
      return Optional.of(new PaymentAddress(d, addressMessage.get().getPkD().toByteArray()));
    } else {
      return Optional.empty();
    }
  }
}
