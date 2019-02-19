package com.concretepage.endpoints;

import com.concretepage.entity.Bill;
import com.concretepage.gs_ws.*;
import com.concretepage.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class BillEndpoint {
	private static final String NAMESPACE_URI = "http://www.concretepage.com/article-ws";
	@Autowired
	private IBillService billService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllBillsRequest")
	@ResponsePayload
	public GetAllBillsResponse getAllBills() {
		GetAllBillsResponse response = new GetAllBillsResponse();
		List<com.concretepage.gs_ws.BillInfo> BillInfoList = new ArrayList<>();
		List<Bill> BillList = billService.retriveAllBills();
		for (int i = 0; i < BillList.size(); i++) {
			 BillInfo ob = new BillInfo();
			 ob.setBillId(BillList.get(i).getId());
			ob.setName(BillList.get(i).getName());
			ob.setUserName(BillList.get(i).getUser().getName());
			ob.setValue(BillList.get(i).getValue());
			ob.setStatus(BillList.get(i).isStatus());
//		    BeanUtils.copyProperties(BillList.get(i), ob);
		     BillInfoList.add(ob);
		}
		response.getBillInfo().addAll(BillInfoList);
		return response;
	}


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "payBillRequest")
	@ResponsePayload
	public PayBillResponse payBill(@RequestPayload PayBillRequest request) {
		PayBillResponse response = new PayBillResponse();
		if( billService.payBill(request.getBillId()))
		{
			response.setStatus(true);
		}
		else
		{
			response.setStatus(false);
		}
		return response;
	}

}
