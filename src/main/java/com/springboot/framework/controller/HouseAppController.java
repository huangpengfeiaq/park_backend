package com.springboot.framework.controller;

import com.springboot.framework.controller.request.HouseInsertRequestBean;
import com.springboot.framework.controller.request.HouseStatusUpdate;
import com.springboot.framework.controller.request.HouseUpdateRequestBean;
import com.springboot.framework.controller.response.PageResponseBean;
import com.springboot.framework.dao.entity.Admin;
import com.springboot.framework.dao.entity.House;
import com.springboot.framework.service.impl.HouseAppServiceImpl;
import com.springboot.framework.util.ResponseEntity;
import com.springboot.framework.util.ResponseEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author SWF
 * @Date 2019/4/25 10:08
 **/
@Api(tags = {"租房应用"}, produces = "application/json")
@RestController
@RequestMapping("/houseApp/")
public class HouseAppController extends BaseController {

    @Autowired
    private HouseAppServiceImpl houseAppService;

    // 列表查看
    @ApiOperation(value = "列表查看", notes = "列表查看")
    @GetMapping(value = "selectList")
    public PageResponseBean selectListByParkId(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam Integer parkId) {
        PageResponseBean page = houseAppService.selectListByParkId(pageNum, pageSize, parkId);
        return page;
    }

    // 查看详情
    // 新增
    @ApiOperation(value = "新增房源", notes = "新增房源")
    @PostMapping(value = "insert")
    public ResponseEntity<Object> insert(@RequestBody HouseInsertRequestBean bean, HttpServletRequest request) {
        House house = new House();
        BeanUtils.copyProperties(bean, house);
        Admin admin = super.getSessionUser(request);
        house.setParkId(admin.getParkId());
        house.setCreateBy(admin.getName());
        return houseAppService.insert(house, bean.getImgs());
    }

    // 查看指定房源图片
    @ApiOperation(value = "查看图片", notes = "查看图片")
    @GetMapping(value = "selectImgs")
    public ResponseEntity<Object> selectImg(@RequestParam Integer houseId) {
        return houseAppService.selectImg(houseId);
    }

    // 删除
    @ApiOperation(value = "删除", notes = "删除房源")
    @DeleteMapping(value = "delete")
    public ResponseEntity<Object> deleteByHouseId(@RequestParam int houseId) {
        return houseAppService.deleteByHouseId(houseId);
    }

    @ApiOperation(value = "修改房源信息", notes = "修改房源信息")
    @PostMapping(value = "update")
    public ResponseEntity<Object> update(@RequestBody HouseUpdateRequestBean bean, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return ResponseEntityUtil.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        Admin admin = super.getSessionUser(request);
        House house = new House();
        BeanUtils.copyProperties(bean, house);
        house.setUpdateBy(super.getSessionUser(request).getName());
        return houseAppService.update(house, bean.getNewImg(), bean.getDelImg(),admin.getParkId());
    }

    @ApiOperation(value = "修改房源状态", notes = "修改房源状态")
    @PostMapping(value = "updateStatus")
    public ResponseEntity<Object> updateStatus(@RequestBody @Valid HouseStatusUpdate bean, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return ResponseEntityUtil.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        Admin admin = super.getSessionUser(request);
        House house = new House();
        house.setId(bean.getId());
        house.setStatus(bean.getStatus());
//        // 如果为重新发布，则重置发布时间
//        if(new Byte("1").equals(bean.getStatus())){
//            house.setCreateDate(new Date());
//        }
        return houseAppService.update(house,null,null,admin.getParkId());
    }
}
