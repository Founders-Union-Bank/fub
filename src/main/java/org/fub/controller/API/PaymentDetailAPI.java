package org.fub.controller.API;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.fub.model.PaymentDetail;
import org.fub.request.PaymentDetailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment Detail")
@Validated
public interface PaymentDetailAPI {

    @Operation(summary = "Create payment details", description = "Create payment details success")
    @ApiResponse(responseCode = "201", description = "Payment Details Created Successfully")
    @PostMapping(value = "/paymentDetails",consumes = {"application/json"},produces = {"application/json"})
    ResponseEntity<PaymentDetail> createPayment(
            @Parameter(in = ParameterIn.DEFAULT, description = "body of the payment details", schema = @Schema()) @Valid @NotNull @RequestBody PaymentDetailRequest paymentDetail);

    @Operation(summary = "Fetch payments detail", description = "Fetch users payment details")
    @ApiResponse(responseCode = "200",description = "Fetch user payment details Success",content = @Content(schema = @Schema(implementation = PaymentDetail.class)))
    @GetMapping(value = "/paymentDetails",produces = {"application/json"})
    ResponseEntity<List<PaymentDetail>> fetchPaymentsDetail(
            @Parameter(in = ParameterIn.QUERY, description = "User of the payment") @NotNull @RequestParam(value = "userId") String userId
    );

    @Operation(summary = "Delete Payment Details",description = "Delete Users payment Details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Payment details deleted successfully"),
            @ApiResponse(responseCode = "404",description = "Payment not found for the given user")
    })
    @DeleteMapping(value = "/paymentDetails/{paymentId}")
    ResponseEntity<String> deletePaymentDetails(
            @Parameter(in = ParameterIn.QUERY, description = "User of the payment") @NotNull @RequestParam(value = "userId") String userId,
            @Parameter(in = ParameterIn.PATH, description = "Id of the payment") @NotNull @PathVariable(value = "paymentId") String paymentId

    );

    @Operation(summary = "Fetch Payment details ",description = "Fetch payment details for particular user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Payment details fetched successfully"),
            @ApiResponse(responseCode = "404",description = "Payment not found for the given user")
    })
    @GetMapping(value = "/paymentDetails/{paymentId}")
    ResponseEntity<PaymentDetail> fetchPaymentDetails(
            @Parameter(in = ParameterIn.QUERY, description = "User of the payment") @NotNull @RequestParam(value = "userId") String userId,
            @Parameter(in = ParameterIn.PATH, description = "Id of the payment") @NotNull @PathVariable(value = "paymentId") String paymentId
    );

    @Operation(summary = "Update Payment details", description = "Update Payment detail of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Payment details updated successfully"),
            @ApiResponse(responseCode = "404",description = "Payment not found for the given user")
    })
    @PutMapping(value = "/paymentDetails/{paymentId}",produces = {"application/json"},consumes = {"application/json"})
    ResponseEntity<PaymentDetail> updatePaymentDetails(
            @Parameter(in = ParameterIn.QUERY, description = "User of the payment") @NotNull @RequestParam(value = "userId") String userId,
            @Parameter(in = ParameterIn.PATH, description = "Id of the payment") @NotNull @PathVariable(value = "paymentId") String paymentId,
            @Parameter(in = ParameterIn.DEFAULT ,description = "Body of the payment") @Valid @NotNull @RequestBody PaymentDetailRequest request
    );
}
