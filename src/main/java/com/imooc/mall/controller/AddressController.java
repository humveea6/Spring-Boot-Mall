package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.AddressAddForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IShippingService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-23
 */
@RestController
public class AddressController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody AddressAddForm form,
                          BindingResult bindingResult,
                          HttpSession session) {
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),form);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(), shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody AddressAddForm form,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(), shippingId, form);
    }

    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                           HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
