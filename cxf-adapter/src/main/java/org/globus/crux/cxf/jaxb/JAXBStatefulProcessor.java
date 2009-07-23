package org.globus.crux.cxf.jaxb;

import org.globus.crux.cxf.StatefulServiceWebProvider;
import org.globus.crux.stateful.MethodProcessor;

import java.lang.reflect.Method;

import org.globus.crux.stateful.Payload;
import org.globus.crux.stateful.StateKey;
import org.globus.crux.stateful.StatefulService;
import org.globus.crux.stateful.StatefulMethod;

import javax.xml.namespace.QName;
import javax.xml.bind.JAXBContext;

/**
 * @author turtlebender
 */
public class JAXBStatefulProcessor implements MethodProcessor {
    JAXBContext jaxb;
    private StatefulServiceWebProvider provider;

    public JAXBStatefulProcessor(JAXBContext jaxb, StatefulServiceWebProvider provider) {
        this.jaxb = jaxb;
        this.provider = provider;
    }    

    public boolean canProcess(Method method) {
        return method.isAnnotationPresent(Payload.class) && method.isAnnotationPresent(StatefulMethod.class);
    }

    public void process(Object toProcess, Method method) {
        Payload payload = method.getAnnotation(Payload.class);
        QName payloadQName = new QName(payload.namespace(), payload.localpart());
        StateKey key = toProcess.getClass().getAnnotation(StatefulService.class).value();
        QName keyQName = new QName(key.namespace(), key.localpart());
        JAXBStatefulHandler handler = new JAXBStatefulHandler(keyQName, toProcess, method, jaxb);
        provider.registerHandler(payloadQName, handler);
    }
}
