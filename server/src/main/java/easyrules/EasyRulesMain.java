package easyrules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.mvel2.ParserContext;

import java.io.FileReader;
import java.math.BigDecimal;
import java.net.URL;

import domain.easyrules.Order;
import lombok.extern.slf4j.Slf4j;

/**
 * EasyRulesMain.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-12-16 : base version.
 */
@Slf4j
public class EasyRulesMain {
  public static void main(String[] args) throws Exception {
    // create 规则引擎
    RulesEngineParameters parameters = new RulesEngineParameters();
    // 当一个规则触发是，是否跳过下一个
    parameters.setSkipOnFirstAppliedRule(false);
    RulesEngine fizzBuzzEngine = new DefaultRulesEngine(parameters);
    // 解析yml ，需要引入类
    ParserContext parserContext = new ParserContext();
    parserContext.addPackageImport("java.math");
    // create rules
    MVELRuleFactory ruleFactory =
        new MVELRuleFactory(new YamlRuleDefinitionReader(), parserContext);
    final URL resource = EasyRulesMain.class.getClassLoader().getResource("rules.yml");
    assert resource != null;
    final Rules rules = ruleFactory.createRules(new FileReader(resource.getFile()));

    // 订单
    final Order order = new Order();
    order.setTotal(new BigDecimal("420"));
    order.setFirstTime(true);
    // fire rules
    Facts facts = new Facts();
    facts.put("order", order);
    fizzBuzzEngine.fire(rules, facts);
    log.info(order.toString());
  }
}
