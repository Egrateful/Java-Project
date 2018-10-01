/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PattyTai
 */
@WebFilter(filterName = "PerformanceFilter", urlPatterns = {"*.do"}, initParams = {
    @WebInitParam(name = "prefix", value = "執行花了:")})
public class PerformanceFilter implements Filter {
    private FilterConfig filterConfig;
    private String prefix = "prefix:";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        String prefix = filterConfig.getInitParameter("prefix");
        if(prefix!=null){
            this.prefix = prefix;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //before
        long begin = System.currentTimeMillis();
        
        chain.doFilter(request, response);
        
        //after
        long spendTime = System.currentTimeMillis()-begin;
        
        filterConfig.getServletContext().log(((HttpServletRequest)request).getRequestURL() + prefix + spendTime + " ms");
    }

    @Override
    public void destroy() {
    }
}
