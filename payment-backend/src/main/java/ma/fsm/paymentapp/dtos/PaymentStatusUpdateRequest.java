package ma.fsm.paymentapp.dtos;

import jakarta.validation.constraints.NotNull;
import ma.fsm.paymentapp.entities.PaymentStatus;

public record PaymentStatusUpdateRequest(@NotNull PaymentStatus status) {
}
