package demo.pay;

import cn.hutool.core.util.IdUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;

/**
 * 支付宝转账
 *
 * @author guild
 */
public class AlipayTransfer {

    public static void main(String[] args) throws AlipayApiException {
        String serverUrl = "https://openapi.alipay.com/gateway.do",
                appId = "",
                privateKey = "",
                alipayPublicKey = "";

        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey,
                "json", "UTF-8", alipayPublicKey, "RSA2");
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        String outBizNo = IdUtil.fastSimpleUUID();
        System.out.println(outBizNo);
        model.setOutBizNo(outBizNo);
        model.setTransAmount("0.1");
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        Participant participant = new Participant();
        participant.setIdentity("");
        participant.setIdentityType("ALIPAY_LOGON_ID");
        participant.setName("");
        model.setPayeeInfo(participant);

        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizModel(model);

        AlipayFundTransUniTransferResponse response = alipayClient.execute(request);
        System.out.println(response.isSuccess());
        System.out.println(response.getBody());
    }
}
