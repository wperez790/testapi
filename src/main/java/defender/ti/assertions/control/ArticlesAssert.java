package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.ArticleWrapper.Article;
import defender.ti.model.ResponseBodyImpl.ArticleWrapper;
import defender.ti.model.ResponseBodyImpl.ArticleIndicatorsWrapper;
import defender.ti.model.ResponseBodyImpl.ArticleIndicatorsWrapper.ArticleIndicators;
import net.thucydides.core.annotations.Step;

import java.util.List;
import java.util.Random;


public class ArticlesAssert extends GenericAssertions {

    InheritableThreadLocal<ArticleWrapper> articleWrapper = new InheritableThreadLocal<>();
    InheritableThreadLocal<ArticleIndicatorsWrapper> articleIndicatorsWrapper = new InheritableThreadLocal<>();

    private ArticleWrapper articleWrapper() {
        return articleWrapper.get();
    }
    private ArticleIndicatorsWrapper articleIndicatorsWrapper() {
        return articleIndicatorsWrapper.get();
    }


    public ArticlesAssert assertThat(ArticleWrapper Article) {
        this.articleWrapper.set(Article);
        return this;
    }
    public ArticlesAssert assertThat(ArticleIndicatorsWrapper wrapper) {
        this.articleIndicatorsWrapper.set(wrapper);
        return this;
    }

    @Step("Validate Article mandatory fields are not empty")
    public ArticlesAssert mandatoryFieldsAreNotEmpty() {
        List<Article> Articles = articleWrapper().getValue();

        for (Article Article: Articles
             ) {
            assertions().assertThat(Article.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(Article.getCreatedDateTime())
                    .describedAs("Created Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(Article.getLastUpdatedDateTime())
                    .describedAs("Last Updated Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(Article.getTitle())
                    .describedAs("Title is not empty")
                    .isNotEmpty();
            assertions().assertThat(Article.getSummary())
                    .describedAs("Summary is not empty")
                    .isNotNull();
            assertions().assertThat(Article.getIsFeatured())
                    .describedAs("Is Featured is not empty")
                    .isNotNull();
            assertions().assertThat(Article.getBody())
                    .describedAs("Body is not empty")
                    .isNotNull();
        }
        return this;
    }

    @Step("Validate Article mandatory fields are not empty")
    public ArticlesAssert areDatesFormattedCorrectly() {
        List<Article> Articles = articleWrapper().getValue();
        for (Article Article: Articles
             ) {
            this.assertBodyFieldValueMatchWith(Article.getCreatedDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(Article.getLastUpdatedDateTime(), RFC3339);
        }
        return this;
    }


    public void skipIsWorkingProperly(ArticleWrapper wrapperListWithoutSkipping, ArticleWrapper wrapperList, int skip) {
        List<Article> arrWithoutSkipping = wrapperListWithoutSkipping.getValue();
        List<Article> list = arrWithoutSkipping.subList(skip, arrWithoutSkipping.size());
        assertions().assertThat(list).describedAs("Articles Skip works properly").isEqualTo(wrapperList.getValue());
    }

    public void topIsWorkingProperly(int top, ArticleWrapper wrapper) {
        int length = wrapper.getValue().size();
        assertions().assertThat(length).describedAs("Top is working properly").isEqualTo(top);
    }

    public void selectIsWorkingProperly(String select) {
        List<Article> articles  = articleWrapper().getValue();
            switch (select) {
                case "createdDateTime":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getCreatedDateTime()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getTitle()).isNull();
                        assertions().assertThat(article.getIsFeatured()).isNull();
                        assertions().assertThat(article.getSummary()).isNull();
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNull();
                        assertions().assertThat(article.getBody()).isNull();
                    }
                case "title":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getTitle()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getCreatedDateTime()).isNull();
                        assertions().assertThat(article.getIsFeatured()).isNull();
                        assertions().assertThat(article.getSummary()).isNull();
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNull();
                        assertions().assertThat(article.getBody()).isNull();
                    }
                case "isFeatured":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getIsFeatured()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getCreatedDateTime()).isNull();
                        assertions().assertThat(article.getTitle()).isNull();
                        assertions().assertThat(article.getSummary()).isNull();
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNull();
                        assertions().assertThat(article.getBody()).isNull();
                    }
                case "lastUpdatedDateTime":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getCreatedDateTime()).isNull();
                        assertions().assertThat(article.getTitle()).isNull();
                        assertions().assertThat(article.getSummary()).isNull();
                        assertions().assertThat(article.getIsFeatured()).isNull();
                        assertions().assertThat(article.getBody()).isNull();
                    }
                case "body":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getBody()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getCreatedDateTime()).isNull();
                        assertions().assertThat(article.getTitle()).isNull();
                        assertions().assertThat(article.getSummary()).isNull();
                        assertions().assertThat(article.getIsFeatured()).isNull();
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNull();
                    }
                case "summary":
                    for (Article article : articles){
                        // Checking select is bringing the fields that you select
                        assertions().assertThat(article.getSummary()).isNotNull();
                        assertions().assertThat(article.getId()).isNotNull();
                        // Checking select is NOT bringing the other fields
                        assertions().assertThat(article.getCreatedDateTime()).isNull();
                        assertions().assertThat(article.getTitle()).isNull();
                        assertions().assertThat(article.getBody()).isNull();
                        assertions().assertThat(article.getIsFeatured()).isNull();
                        assertions().assertThat(article.getLastUpdatedDateTime()).isNull();
                    }
            }

    }

    public void searchIsWorkingProperly(String search) {
        Integer random = new Random().nextInt(articleWrapper().getValue().size() - 1);
        String actualBody = articleWrapper().getValue().get(random).getBody().getContent();
        assertions().assertThat(actualBody).as("The body doesn't contain the word '%s'", search).contains(search);
    }



    @Step("Validate Article mandatory fields are not empty")
    public ArticlesAssert mandatoryFieldsAreNotEmptyFromIndicators() {
        List<ArticleIndicators> articlesIndicators = articleIndicatorsWrapper().getValue();

        for (ArticleIndicators indicator: articlesIndicators
        ) {
            assertions().assertThat(indicator.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(indicator.getSource())
                    .describedAs("Created Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(indicator.getArtifact())
                    .describedAs("Last Updated Date Time is not empty")
                    .isNotNull();
        }
        return this;
    }


    public void topIsWorkingProperly(int top) {
        ArticleIndicatorsWrapper articleWrapper = articleIndicatorsWrapper();
        assertions()
                .assertThat(articleWrapper.getValue().size())
                .describedAs("Top is working properly").isEqualTo(top);
    }

    public void skipIsWorkingProperly(ArticleIndicatorsWrapper wrapperListWithoutSkipping, ArticleIndicatorsWrapper wrapperList, int skip) {
        List<ArticleIndicators> listWithoutSkipping = wrapperListWithoutSkipping.getValue();
        List<ArticleIndicators> list = listWithoutSkipping.subList( skip, listWithoutSkipping.size());
        assertions().assertThat(list).describedAs("Articles Indicators Skip works properly").isEqualTo(wrapperList.getValue());
    }


    public void selectIsWorkingProperlyInIndicators(String select) {
        List<ArticleIndicators> indicators  = articleIndicatorsWrapper().getValue();
        switch (select) {
            case "source":
                for (ArticleIndicators indicator : indicators){
                    // Checking select is bringing the fields that you select
                    assertions().assertThat(indicator.getSource()).isNotNull();
                    assertions().assertThat(indicator.getId()).isNotNull();
                    // Checking select is NOT bringing the other fields
                    assertions().assertThat(indicator.getArtifact()).isNull();
                }
            case "id":
                for (ArticleIndicators indicator : indicators){
                    // Checking select is bringing the fields that you select
                    assertions().assertThat(indicator.getId()).isNotNull();
                    // Checking select is NOT bringing the other fields
                    assertions().assertThat(indicator.getSource()).isNull();
                    assertions().assertThat(indicator.getArtifact()).isNull();
                }
        }

    }
    public void searchIsWorkingProperlyInIndicators(String search) {
        Integer random = new Random().nextInt(articleIndicatorsWrapper().getValue().size() - 1);
        String source = articleIndicatorsWrapper().getValue().get(random).getSource();
        assertions().assertThat(source).as("The source doesn't contain the word '%s'", search).contains(search);
    }

    @Step("Validate that the response is the Last Page")
    public ArticlesAssert assertIsLastPage(Integer top) {
        List<ArticleWrapper.Article> articles = articleWrapper().getValue();
        assertions().assertThat(articleWrapper().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(articles.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }

}
