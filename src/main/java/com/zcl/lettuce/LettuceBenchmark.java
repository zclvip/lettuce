package com.zcl.lettuce;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisStringCommands;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import com.zcl.domain.OrderInfo;
import com.zcl.kryo.KryoSerialization;
import com.zcl.util.JacksonUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhaocl1 on 2018/4/25.
 */
@State(Scope.Benchmark)
public class LettuceBenchmark {
    private RedisClient client = null;
    private StatefulRedisConnection<byte[],byte[]> connection;
    private KryoSerialization kryo = new KryoSerialization();
    private OrderInfo orderInfo;
    private String key = "order_";
    AtomicLong orderId = new AtomicLong(0);


    @Setup
    public void setup(){
        client = RedisClient.create("redis://localhost");
        connection = client.connect(ByteArrayCodec.INSTANCE);
        kryo.register(OrderInfo.class);
        orderInfo = createOrderInfo();
    }

    public static OrderInfo createOrderInfo(){
        String result = "{\"orderId\":11731619,\"memberId\":\"\",\"memberCode\":\"18612916096\",\"lenovoId\":\"10061259911\",\"merchantId\":1,\"platId\":2,\"sourceId\":0,\"orderInfo\":\"{\\\"totalCost\\\":0.0,\\\"totalPay\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"freight\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"giveawayCost\\\":0.0,\\\"giveawayTotal\\\":0.0,\\\"discount\\\":0.0,\\\"couponsDiscount\\\":0.0,\\\"lipinkaDiscount\\\":0.0,\\\"creditTotal\\\":0,\\\"rePrice\\\":0.0,\\\"reCoupons\\\":0.0,\\\"paymentTypeId\\\":\\\"0\\\",\\\"notes\\\":\\\"\\\",\\\"submitTime\\\":1524640901570,\\\"shipmentTypeID\\\":0,\\\"clientIP\\\":\\\"61.148.244.241\\\",\\\"userLevel\\\":4,\\\"isRegister\\\":0,\\\"addOrderType\\\":\\\"1\\\",\\\"customerManagerCode\\\":\\\"\\\",\\\"deliverid\\\":\\\"3415900\\\",\\\"orderRefer\\\":\\\"12\\\",\\\"contractNo\\\":\\\"dc3a70d6-77bc-4409-80c6-13a60c2d884f\\\",\\\"enrolledGroup\\\":\\\"1\\\",\\\"enterprise\\\":\\\"\\\",\\\"enterpriseNo\\\":\\\"\\\",\\\"enterpriseType\\\":\\\"0\\\",\\\"ordercomefrom\\\":\\\"0\\\",\\\"deliveryAddress\\\":{\\\"code\\\":\\\"F\\\",\\\"name\\\":\\\"付婧\\\",\\\"address\\\":\\\"双桥东路,远洋一方,花语苑12-2-801\\\",\\\"zip\\\":\\\"\\\",\\\"mobile\\\":\\\"18612916096\\\",\\\"phone\\\":\\\"\\\",\\\"email\\\":\\\"\\\",\\\"province\\\":\\\"北京\\\",\\\"city\\\":\\\"北京\\\",\\\"county\\\":\\\"朝阳\\\",\\\"provinceId\\\":\\\"010\\\",\\\"cityId\\\":\\\"1185\\\",\\\"countyId\\\":\\\"10997\\\",\\\"addressId\\\":\\\"\\\",\\\"guid\\\":\\\"\\\",\\\"innerAddressId\\\":\\\"3415900\\\"},\\\"orderInvoice\\\":{\\\"invoiceTypeId\\\":1,\\\"invoiceContent\\\":\\\"木星影业（北京）股份有限公司\\\",\\\"invoiceHeader\\\":\\\"1\\\",\\\"taxpayerIdentity\\\":\\\"91110101799974438Q\\\",\\\"status\\\":\\\"0\\\",\\\"invoiceId\\\":\\\"94156\\\",\\\"taxNoType\\\":2,\\\"isNeedMerge\\\":0},\\\"isSendHt\\\":0,\\\"submitOrderWay\\\":1,\\\"tenant\\\":{\\\"shopId\\\":1,\\\"currencyCode\\\":\\\"CNY\\\",\\\"language\\\":\\\"zh\\\",\\\"timeZone\\\":\\\"Asia/Shanghai\\\",\\\"nationalCode\\\":\\\"CN\\\",\\\"nationalName\\\":\\\"中国\\\",\\\"currencySign\\\":\\\"?\\\",\\\"timeFormat\\\":\\\"yyyy-MM-dd HH:mm:ss\\\",\\\"name\\\":\\\"联想商城\\\",\\\"domain\\\":\\\"lenovo.com.cn\\\",\\\"pcDomain\\\":\\\"shop.lenovo.com.cn\\\",\\\"wapDomain\\\":\\\"m.lenovo.com.cn\\\",\\\"groupId\\\":\\\"C0001\\\",\\\"languageName\\\":\\\"简体中文\\\"},\\\"totalTax\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"faInfoVos\\\":[{\\\"faid\\\":\\\"8419b549-a5ad-4fd5-82e4-883862eb71fb\\\",\\\"faType\\\":\\\"1\\\",\\\"orderInvoice\\\":{\\\"invoiceTypeId\\\":1,\\\"invoiceContent\\\":\\\"木星影业（北京）股份有限公司\\\",\\\"invoiceHeader\\\":\\\"1\\\",\\\"taxpayerIdentity\\\":\\\"91110101799974438Q\\\",\\\"status\\\":\\\"0\\\",\\\"invoiceId\\\":\\\"94156\\\",\\\"taxNoType\\\":2,\\\"isNeedMerge\\\":0}}],\\\"version\\\":\\\"I18N-MONEY\\\",\\\"codinform\\\":0,\\\"codtime\\\":0}\",\"cartInfo\":\"{\\\"num\\\":1,\\\"totalNum\\\":0,\\\"totalNumChecked\\\":0,\\\"vendors\\\":[{\\\"id\\\":\\\"8419b549-a5ad-4fd5-82e4-883862eb71fb\\\",\\\"name\\\":\\\"B2C北京银思朗能源技术有限公司\\\",\\\"type\\\":1,\\\"faCode\\\":\\\"111045015\\\",\\\"sorted\\\":[{\\\"itemType\\\":0,\\\"item\\\":{\\\"item\\\":\\\"Sku\\\",\\\"itemId\\\":\\\"1000243\\\",\\\"id\\\":\\\"1000243\\\",\\\"num\\\":1,\\\"check\\\":1,\\\"updateTme\\\":\\\"2018-04-25 15:18:08\\\",\\\"name\\\":\\\"联想Lecoo“倍爱宝”宝宝成长记录仪A1\\\",\\\"rowNo\\\":0,\\\"gdesc\\\":\\\"晒娃神器 智能摄像机 1080P云台版AI人工智能 智能监控\\\",\\\"cid\\\":\\\"090C\\\",\\\"imgUrl\\\":\\\"//p2.lefile.cn/product/adminweb/2018/04/19/3dH8zOdyuBP4yNNth8UvWG7aO-7665.jpg\\\",\\\"gspec\\\":[],\\\"isNeedSN\\\":0,\\\"isPhysical\\\":1,\\\"price\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"virtualCoinType\\\":-1,\\\"discount\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"stock\\\":0,\\\"isDelivery\\\":0,\\\"shopId\\\":1,\\\"faType\\\":1,\\\"productGroupId\\\":\\\"1000\\\",\\\"salesType\\\":0,\\\"unit\\\":\\\"台\\\",\\\"deatLike\\\":\\\"1000\\\",\\\"thinkRemark\\\":\\\"\\\",\\\"internalMedia\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"costPrice\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"internalUpLine\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"terminal\\\":31,\\\"maxPurchase\\\":50,\\\"activityType\\\":2,\\\"extraType\\\":0,\\\"activityId\\\":8543,\\\"marketable\\\":true,\\\"gifts\\\":[],\\\"canSelectPromotions\\\":[],\\\"services\\\":[],\\\"containsService\\\":false,\\\"options\\\":[],\\\"freeShipping\\\":false,\\\"freeFinancingRate\\\":false,\\\"rcServCheck\\\":false,\\\"isService\\\":0,\\\"skuExtend\\\":{\\\"cvItemList\\\":[]},\\\"optionGift\\\":[],\\\"typeId\\\":\\\"f3320f8c-a7d2-4fea-918e-5d30b5c5ebfd\\\",\\\"isIndependentSale\\\":1,\\\"serviceType\\\":0,\\\"url\\\":\\\"https://m.lenovo.com.cn/product/1000243.html\\\",\\\"faCode\\\":\\\"111045015\\\",\\\"personalizations\\\":[],\\\"taxRate\\\":0,\\\"showInCart\\\":true,\\\"uniqueKey\\\":0,\\\"hsSilent\\\":false,\\\"adjustments\\\":[],\\\"discountInfoList\\\":[],\\\"attributes\\\":{},\\\"type\\\":0,\\\"faid\\\":\\\"8419b549-a5ad-4fd5-82e4-883862eb71fb\\\",\\\"materialCode\\\":\\\"le070101002\\\",\\\"bu\\\":70050,\\\"totalDiscount\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"faname\\\":\\\"B2C北京银思朗能源技术有限公司\\\",\\\"useActivity\\\":true,\\\"zc\\\":false,\\\"personality\\\":false,\\\"vendorid\\\":\\\"8419b549-a5ad-4fd5-82e4-883862eb71fb\\\"}}],\\\"total\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"totalDiscount\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"afterDiscount\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"}}],\\\"totalPrice\\\":{\\\"amount\\\":\\\"699.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"totalDiscount\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"comefrom\\\":\\\"0\\\",\\\"rowNumLimit\\\":0,\\\"totalTax\\\":{\\\"amount\\\":\\\"0.00\\\",\\\"currencyCode\\\":\\\"CNY\\\"},\\\"empty\\\":false,\\\"secKill\\\":false,\\\"nonCombinablePromoApplied\\\":false}\",\"status\":0,\"createTime\":\"2018-04-25 15:21:41\",\"readTime\":null,\"readTimes\":0,\"shopId\":1,\"terminal\":2,\"currencyCode\":\"CNY\"}";
        System.out.println("string size="+result.getBytes().length);
        long start = System.currentTimeMillis();
        OrderInfo orderInfo = JacksonUtil.fromJson(result, OrderInfo.class);
        long end = System.currentTimeMillis() - start;
        System.out.println("json :"+end);
        return orderInfo;
    }

    @TearDown
    public void tearDown(){
        connection.close();
        client.shutdown();
    }

    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 5)
    @Threads(100)
    @Measurement(iterations = 20,time = 5,timeUnit = TimeUnit.SECONDS)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Benchmark
    public void save(){
        long id  = orderId.incrementAndGet();
        orderInfo.setOrderId(id);
        byte[] data = kryo.Serialize(orderInfo);
        RedisStringCommands<byte[],byte[]> sync = connection.sync();
        sync.set((key+id).getBytes(), data);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(LettuceBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
