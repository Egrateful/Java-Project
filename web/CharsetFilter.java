/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.web;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 *
 * @author PattyTai
 */
@WebFilter(filterName = "CharsetFilter", urlPatterns = {"*.jsp","*.do","*.asp","*.view","*.php"}, initParams = {
    @WebInitParam(name = "charset", value = "big5")}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR})
public class CharsetFilter implements Filter {
    private FilterConfig filterConfig;
    private String charset="UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        String charset = filterConfig.getInitParameter("charset");
        if(charset!=null){
            this.charset = charset;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //before process...
        request.setCharacterEncoding(charset);
        request.getParameterNames();
        
        
        response.setCharacterEncoding(charset);
        response.getWriter();
        
        chain.doFilter(request, response);
        
        //after process...
        
    }

    @Override
    public void destroy() {
        
    }
}
