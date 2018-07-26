package cn.edu.zju.bank.controller;

import cn.edu.zju.bank.constants.ShopkeeperConstant;
import cn.edu.zju.bank.domain.req.BankcardReq;
import cn.edu.zju.bank.domain.res.BaseRes;
import cn.edu.zju.bank.domain.res.ObjectRes;
import cn.edu.zju.bank.domain.vo.BankcardVO;
import cn.edu.zju.bank.enums.ResultEnum;
import cn.edu.zju.bank.exception.BankcardException;
import cn.edu.zju.bank.service.BankcardService;
import cn.edu.zju.bank.utils.BankNumberUtil;
import com.alibaba.fastjson.JSONObject;
import javafx.geometry.Pos;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wang Zejie
 * @version V1.0
 * @date 2018/7/24 下午2:49
 * Description 银行卡控制类
 */
@Controller
@RequestMapping("")
public class BankcardController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BankcardController.class);

    private BankcardService bankcardService;

    public BankcardController(BankcardService bankcardService) {
        this.bankcardService = bankcardService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object index() {
        return ShopkeeperConstant.VIEW_INDEX;
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Object getInfo(@RequestParam String bankcardNumber,
                          @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        ObjectRes<BankcardVO> res = new ObjectRes<>();
        BankcardReq req = new BankcardReq();
        req.setPassword(password);
        try {
            req.setBankcardNumber(Long.parseLong(bankcardNumber));
            res = bankcardService.getBankcardByNumber(req);
            jsonObject.put(ShopkeeperConstant.BALANCE, res.getResultObj().getBalance());
        } catch (BankcardException e) {
            logger.error("BankcardController createBankcard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("BankcardController createBankcard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public Object getNewCard() {
        return ShopkeeperConstant.VIEW_NEW_CARD;
    }

    @RequestMapping(value = "/card", method = RequestMethod.POST)
    @ResponseBody
    public Object createNewCard(@RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        BaseRes res = new BaseRes();
        BankcardReq req = new BankcardReq();
        req.setPassword(password);
        String bankcardNumber;
        try {
            bankcardNumber = BankNumberUtil.getBrankNumber("6");
            if (bankcardNumber == null) {
                throw new Exception();
            }
            req.setBankcardNumber(Long.parseLong(bankcardNumber));
            res = bankcardService.createBankcard(req);
            jsonObject.put(ShopkeeperConstant.BANKCARD_NUMBER, bankcardNumber);
        } catch (BankcardException e) {
            logger.error("BankcardController createNewCard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(e.getErrorCode());
            res.setResultMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("BankcardController createNewCard error:{}", ExceptionUtils.getStackTrace(e));
            res.setResultCode(ResultEnum.SYSTEM_ERROR.getCode());
            res.setResultMsg(ResultEnum.SYSTEM_ERROR.getMsg());
        }
        jsonObject.put(ShopkeeperConstant.RESULT_CODE, res.getResultCode());
        jsonObject.put(ShopkeeperConstant.RESULT_MSG, res.getResultMsg());
        return jsonObject;
    }
}
