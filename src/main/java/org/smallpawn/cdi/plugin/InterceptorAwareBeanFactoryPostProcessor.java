package org.smallpawn.cdi.plugin;

import org.smallpawn.cdi.plugin.model.InterceptorInfo;
import org.smallpawn.cdi.plugin.model.InterceptorMetaDataBean;
import org.smallpawn.cdi.plugin.strategies.impl.SimpleInterceptorOrderingStrategy;
import org.smallpawn.cdi.plugin.strategies.impl.SimpleInterceptorResolutionStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link BeanFactoryPostProcessor} that creates and registers the {@link InterceptorMetaDataBean} and the
 * {@link InterceptorAwareBeanPostProcessor}.
 * 
 * @author Niklas Schlimm
 * 
 */
@SuppressWarnings("rawtypes")
public class InterceptorAwareBeanFactoryPostProcessor implements BeanFactoryPostProcessor, InitializingBean {

	private static final String INTERCEPTOR_META_DATA_BEAN = "interceptorMetaDataBean";

	/**
	 * Strategy to resolve available interceptors
	 */
	private org.smallpawn.cdi.plugin.strategies.InterceptorResolutionStrategy interceptorResolutionStrategy;

	/**
	 * Strategy to order the available interceptors
	 */
	private org.smallpawn.cdi.plugin.strategies.InterceptorOrderingStrategy interceptorOrderingStrategy;

	private List<Class> interceptorOrder = new ArrayList<Class>();

	public InterceptorAwareBeanFactoryPostProcessor() {
		super();
	}

	public InterceptorAwareBeanFactoryPostProcessor(org.smallpawn.cdi.plugin.strategies.InterceptorResolutionStrategy interceptorResolutionStrategy) {
		super();
		this.interceptorResolutionStrategy = interceptorResolutionStrategy;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		((DefaultListableBeanFactory) beanFactory).registerBeanDefinition("_interceptorPostProcessor", BeanDefinitionBuilder.rootBeanDefinition(org.smallpawn.cdi.plugin.processor.InterceptorAwareBeanPostProcessor.class)
				.getBeanDefinition());
		if (beanFactory.getBeanNamesForType(org.smallpawn.cdi.plugin.processor.InterceptorAwareBeanPostProcessor.class) == null) {
			throw new org.smallpawn.cdi.plugin.InterceptorAwareBeanFactoryPostProcessorException("Spring-CDI interceptor module requires DecoratorAwareBeanPostProcessor registered!");
		}
		createAndRegisterMetaDataBean(beanFactory);
	}

	public InterceptorMetaDataBean createAndRegisterMetaDataBean(ConfigurableListableBeanFactory beanFactory) {
		List<InterceptorInfo> interceptors = interceptorResolutionStrategy.resolveRegisteredInterceptors(beanFactory);
		interceptors = interceptorOrderingStrategy.orderInterceptors(beanFactory, interceptors, getInterceptorOrder());
		InterceptorMetaDataBean metaDataBean = new InterceptorMetaDataBean(interceptors);
		beanFactory.registerSingleton(INTERCEPTOR_META_DATA_BEAN, metaDataBean);
		return metaDataBean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (interceptorResolutionStrategy == null) {
			interceptorResolutionStrategy = new SimpleInterceptorResolutionStrategy();
		}
		if (interceptorOrderingStrategy == null) {
			interceptorOrderingStrategy = new SimpleInterceptorOrderingStrategy();
		}
	}

	public void setInterceptorOrder(List<Class> interceptorOrder) {
		this.interceptorOrder = interceptorOrder;
	}

	public List<Class> getInterceptorOrder() {
		return interceptorOrder;
	}

}
