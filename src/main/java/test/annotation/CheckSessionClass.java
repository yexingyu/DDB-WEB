/**
 *
 */
package test.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author x_ye
 */
@Component
public class CheckSessionClass implements HandlerMethodReturnValueHandler, HandlerMethodArgumentResolver {

  @Override
  public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest arg2, WebDataBinderFactory arg3) throws Exception {
    System.out.println("......MY ANNOTATION CALLEDD.....resolveArgument");
    return null;
  }

  @Override
  public boolean supportsParameter(MethodParameter arg0) {
    System.out.println("......MY ANNOTATION CALLEDD.....supportsParameter");
    return false;
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
      throws Exception {
    CheckSession annotation;
    annotation = returnType.getMethodAnnotation(CheckSession.class);
    if (annotation.isAuthenticate()) {
      System.out.println("......got request is aurhenticated..true");
    } else {
      System.out.println("......got request is aurhenticated..false");
    }
  }

  @Override
  public boolean supportsReturnType(MethodParameter arg0) {
    System.out.println("......MY ANNOTAION CALLEDD.....supportsReturnType");
    return false;
  }

}
