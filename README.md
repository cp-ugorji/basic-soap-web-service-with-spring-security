# basic-soap-web-service-with-spring-security

<Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
    <Header>
        <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" mustUnderstand="1">
            <wsse:UsernameToken>
                <wsse:Username>User</wsse:Username>
                <wsse:Password>Password</wsse:Password>
            </wsse:UsernameToken>
        </wsse:Security>
    </Header>
    <Body>
        <GetCourseDetailsRequest xmlns="http://stealth.com/courses">
            <id>1</id>
        </GetCourseDetailsRequest>
    </Body>
</Envelope>
