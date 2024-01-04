package com.etech7.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.etech7.dto.ConnectWiseAutomateIntegration;
import com.etech7.dto.ConnectWiseManageIntegration;
import com.etech7.dto.EmailConnectorIntegration;
import com.etech7.dto.MicrosoftGraphIntegration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Integrations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Integrations {
    @Id
    private String id;
//    @Indexed(unique = true)
    private String mspCustomDomain;
	private boolean connectWiseManageIntegrator;
	private boolean connectWiseAutomateIntegrator;
	private boolean microsoftGraphIntegrator;
	private boolean emailIntegrator;
    private ConnectWiseManageIntegration connectWiseManageIntegration;
    private ConnectWiseAutomateIntegration connectWiseAutomateIntegration;
    private EmailConnectorIntegration emailConnectorIntegration;
    private MicrosoftGraphIntegration microsoftGraphIntegration;
}
