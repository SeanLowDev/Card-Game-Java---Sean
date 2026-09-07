package rummy.strategy;

public class DecisionStrategyFactory {

    private static DecisionStrategyFactory instance;

    public static DecisionStrategyFactory getInstance() {
        if (instance == null)
            instance = new DecisionStrategyFactory();
        return instance;
    }

    public IDecisionStrategy getDecision(){
         CountDecisionStrategy compositeDecisionCriterion = new CountDecisionStrategy();
         compositeDecisionCriterion.add(new Criterion1DecisionStrategy());
         compositeDecisionCriterion.add(new Criterion2DecisionStrategy());
         compositeDecisionCriterion.add(new Criterion3DecisionStrategy());
         compositeDecisionCriterion.add(new Criterion4DecisionStrategy());
         return compositeDecisionCriterion;
    }

}
