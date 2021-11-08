package cn.lili.controller.statistics;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.security.context.UserContext;
import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.order.order.entity.dos.AfterSale;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.service.AfterSaleService;
import cn.lili.modules.order.order.service.OrderService;
import cn.lili.modules.statistics.entity.dto.StatisticsQueryParam;
import cn.lili.modules.statistics.entity.vo.OrderOverviewVO;
import cn.lili.modules.statistics.entity.vo.OrderStatisticsDataVO;
import cn.lili.modules.statistics.service.OrderStatisticsDataService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 店铺端,订单统计接口
 *
 * @author Bulbasaur
 * @since 2020/12/9 19:04
 */
@Slf4j
@Api(tags = "店铺端,订单统计接口")
@RestController
@RequestMapping("/store/statistics/order")
public class OrderStatisticsStoreController {

    /**
     * 订单
     */
    @Autowired
    private OrderService orderService;
    /**
     * 售后
     */
    @Autowired
    private AfterSaleService afterSaleService;
    /**
     * 订单统计
     */
    @Autowired
    private OrderStatisticsDataService orderStatisticsDataService;

    @ApiOperation(value = "订单概览统计")
    @GetMapping("/overview")
    public ResultMessage<OrderOverviewVO> overview(StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderStatisticsDataService.overview(statisticsQueryParam));
        } catch (Exception e) {
            log.error("订单概览统计错误", e);
        }
        return null;
    }

    @ApiOperation(value = "订单图表统计")
    @GetMapping
    public ResultMessage<List<OrderStatisticsDataVO>> statisticsChart(StatisticsQueryParam statisticsQueryParam) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderStatisticsDataService.statisticsChart(statisticsQueryParam));
        } catch (Exception e) {
            log.error("订单图表统计错误", e);
        }
        return null;
    }


    @ApiOperation(value = "订单统计")
    @GetMapping("/order")
    public ResultMessage<IPage<OrderSimpleVO>> order(StatisticsQueryParam statisticsQueryParam, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        try {
            statisticsQueryParam.setStoreId(storeId);
            return ResultUtil.data(orderService.getStatistics(statisticsQueryParam, pageVO));
        } catch (Exception e) {
            log.error("订单统计错误", e);
        }
        return null;
    }


    @ApiOperation(value = "退单统计")
    @GetMapping("/refund")
    public ResultMessage<IPage<AfterSale>> refund(StatisticsQueryParam statisticsQueryParam, PageVO pageVO) {
        String storeId = Objects.requireNonNull(UserContext.getCurrentUser()).getStoreId();
        statisticsQueryParam.setStoreId(storeId);
        return ResultUtil.data(afterSaleService.getStatistics(statisticsQueryParam, pageVO));
    }
}
