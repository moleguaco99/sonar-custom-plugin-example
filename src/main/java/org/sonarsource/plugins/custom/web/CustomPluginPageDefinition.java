package org.sonarsource.plugins.custom.web;

import org.sonar.api.web.page.Context;
import org.sonar.api.web.page.Page;
import org.sonar.api.web.page.PageDefinition;

import static org.sonar.api.web.page.Page.Qualifier.SUB_VIEW;
import static org.sonar.api.web.page.Page.Qualifier.VIEW;
import static org.sonar.api.web.page.Page.Scope.COMPONENT;

public class CustomPluginPageDefinition implements PageDefinition {

    @Override
    public void define(Context context) {
        context.addPage(Page.builder("td/metrics_page")
                .setName("Metrics page")
                .setScope(COMPONENT)
                .setComponentQualifiers(VIEW, SUB_VIEW)
                .build());
    }
}
