package com.gmail.chen.ui.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gmail.chen.backend.Database;
import com.gmail.chen.backend.PrimaryContent;
import com.gmail.chen.ui.MainLayout;
import com.gmail.chen.ui.components.detailsdrawer.DetailsDrawer;
import com.gmail.chen.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.gmail.chen.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.gmail.chen.ui.util.LumoStyles;
import com.gmail.chen.ui.util.UIUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DebouncePhase;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route(value = "products", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Products")
public class Products extends SplitViewFrame implements RouterLayout {

    private Grid<PrimaryContent> grid;
    private Database db = Database.getInstance();
    private DetailsDrawer detailsDrawer;
    private String table = "product";
    private PrimaryContent p = null;
	private DetailsDrawerHeader detailsDrawerHeader;
	private String currentName;
	private ListDataProvider<PrimaryContent> dataProvider;
    
    public Products() {
    	grid = createGrid();
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
    }

    private Component createContent() {
        Div content = new Div(addToolBar(), grid);
        content.addClassName("grid-view");
        return content;
    }
    
    @SuppressWarnings("unchecked")
	private Component addToolBar() {        
        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search...");
        searchBar.setWidth("30%");
        searchBar.setValueChangeMode(ValueChangeMode.EAGER);
        searchBar.setPrefixComponent(VaadinIcon.SEARCH.create());
        Icon closeIcon = new Icon("lumo", "cross");
        closeIcon.setVisible(false);
        ComponentUtil.addListener(closeIcon, ClickEvent.class,
                (ComponentEventListener) e -> searchBar.clear());
        searchBar.setSuffixComponent(closeIcon);
        
        dataProvider = (ListDataProvider<PrimaryContent>)grid.getDataProvider();
        
        searchBar.getElement().addEventListener("value-changed", event -> {
            closeIcon.setVisible(!searchBar.getValue().isEmpty());
           dataProvider.addFilter(
                    p -> StringUtils.containsIgnoreCase(p.getID(),
                            searchBar.getValue()));
        }).debounce(300, DebouncePhase.TRAILING);
        
        Button addItem = new Button("Add Item");
        addItem.setThemeName("primary");
        addItem.addClickListener(e -> addItemForm().open());
        
        ComboBox<String> tableBox = new ComboBox<>();
        tableBox.setItems("Amazon", "Ebay");
        tableBox.setPlaceholder("Select Websource");
        tableBox.addValueChangeListener(event -> {
            if (!event.getSource().isEmpty()) {
                this.table = event.getValue();
                db.retriveAllContent(table);
                grid.setDataProvider(
                        DataProvider.ofCollection(db.getAllContents()));
            }
        });

        HorizontalLayout toolBar = new HorizontalLayout(searchBar, addItem, tableBox);
        return toolBar;
    }
    
    private Dialog addItemForm() {
    	Dialog dialog = new Dialog();
    	PrimaryContent primaryContent = new PrimaryContent();
    	
    	TextField name = new TextField();
        name.setWidth("100%");
        name.addValueChangeListener(e -> primaryContent.id = e.getValue());
        
        TextField type = new TextField();
        type.setWidth("100%");
        type.addValueChangeListener(e -> primaryContent.primary = e.getValue());
        
        TextField color = new TextField();
        color.setWidth("100%");
        color.addValueChangeListener(e -> primaryContent.primary_eng = e.getValue());

        TextField size = new TextField();
        size.setWidth("100%");
        size.addValueChangeListener(e -> primaryContent.secondary = e.getValue());
        
        TextField weight = new TextField();
        weight.setWidth("100%");
        weight.addValueChangeListener(e -> primaryContent.secondary_eng = e.getValue());

        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
        form.addFormItem(name, "SKU");
        form.addFormItem(type, "一级目录");
        form.addFormItem(color, "Primary");
        form.addFormItem(size, "二级目录");
        form.addFormItem(weight, "Secondary");
        
        HorizontalLayout buttonBar = new HorizontalLayout();  

        Button addButton = new Button("Add");
        addButton.setThemeName("primary");
        List<String> imgList = new ArrayList<>();
		for(Integer i = 0; i < 10; i++) {
			imgList.add(i.toString());
		}
        addButton.addClickListener(e -> {
        		db.insertContents(primaryContent, table); 
        		db.retriveAllContent(table);
        		grid.setDataProvider(
                        DataProvider.ofCollection(db.getAllContents()));
        		dialog.close();});
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setThemeName("primary");
        cancelButton.addClickListener(e -> dialog.close());
        buttonBar.add(addButton, cancelButton);
        
        dialog.add(new Div(form, buttonBar));
        return dialog;
    }

    private Grid<PrimaryContent> createGrid() {
    	db.retriveAllContent(table);
    	TreeGrid<PrimaryContent> grid = new TreeGrid<>();
    	grid.setItems(db.getAllContents(), item -> {
    	    if ((item.getLevel() == 0 && item.getId() > 10)
    	            || item.getLevel() > 1) {
    	        return Collections.emptyList();
    	    }
    	    if (!childMap.containsKey(item)) {
    	        childMap.put(item, createSubItems(81, item.getLevel() + 1));
    	    }
    	    return childMap.get(item);
    	});
    	grid.addHierarchyColumn(PrimaryContent::getfirstName).setHeader("Hierarchy");
    	grid.addColumn(Person::getAge).setHeader("Age");

    	grid.addExpandListener(event -> message.setValue(
    	        String.format("Expanded %s item(s)", event.getItems().size())
    	                + "\n" + message.getValue()));
    	grid.addCollapseListener(event -> message.setValue(
    	        String.format("Collapsed %s item(s)", event.getItems().size())
    	                + "\n" + message.getValue()));
        grid.setId("products");
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(item -> viewDetails(item, grid)));
        grid.setDataProvider(
                DataProvider.ofCollection(db.getAllContents()));
        grid.setSizeFull();

        grid.addColumn(PrimaryContent::getID).setFlexGrow(0)
                .setHeader("SKU").setFrozen(true).setSortable(isVisible())
                .setWidth(UIUtils.COLUMN_WIDTH_M).setResizable(true);
        grid.addColumn(PrimaryContent::getPrimary)
                .setHeader("一级目录").setWidth(UIUtils.COLUMN_WIDTH_S)
                .setResizable(true);
        grid.addColumn(PrimaryContent::getPrimaryEng).setHeader("Primary")
                .setWidth(UIUtils.COLUMN_WIDTH_S).setResizable(true);
        grid.addColumn(PrimaryContent::getSecondary).setHeader("二级目录")
        		.setWidth(UIUtils.COLUMN_WIDTH_M).setResizable(true);
        grid.addColumn(PrimaryContent::getSecondaryEng)
                .setHeader("Secondary").setWidth(UIUtils.COLUMN_WIDTH_S)
                .setResizable(true);
        grid.addComponentColumn(item -> createRemoveButton(grid, item))
        		.setFlexGrow(0).setWidth("130px")
        		.setResizable(true).setTextAlign(ColumnTextAlign.CENTER);
        return grid;
    }
    
    private FormLayout createDetails(PrimaryContent primaryContent){
    	try {
			p = (PrimaryContent) primaryContent.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
    	
        TextField name = new TextField();
        name.setValue(primaryContent.getID());
        name.setWidth("100%");
        name.addValueChangeListener(e -> p.id = e.getValue());
        
        TextField type = new TextField();
        type.setValue(primaryContent.getPrimary());
        type.setWidth("100%");
        type.addValueChangeListener(e -> p.primary = e.getValue());
        
        TextField color = new TextField();
        color.setValue(primaryContent.getPrimaryEng());
        color.setWidth("100%");
        color.addValueChangeListener(e -> p.primary_eng = e.getValue());

        TextField size = new TextField();
        size.setValue(primaryContent.getSecondary());
        size.setWidth("100%");
        size.addValueChangeListener(e -> p.secondary = e.getValue());
        
        TextField weight = new TextField();
        weight.setValue(primaryContent.getSecondaryEng());
        weight.setWidth("100%");
        weight.addValueChangeListener(e -> p.secondary_eng = e.getValue());
        
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
        
        FormLayout.FormItem nameItem = form.addFormItem(name, "SKU");
        FormLayout.FormItem typeItem = form.addFormItem(type, "一级目录");
        FormLayout.FormItem colorItem = form.addFormItem(color, "Primary");
        FormLayout.FormItem sizeItem = form.addFormItem(size, "Size");
        FormLayout.FormItem weightItem = form.addFormItem(weight, "二级目录");
    
        UIUtils.setColSpan(2, nameItem, typeItem, colorItem, sizeItem, weightItem);
        return form;
    }
    
    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);
        
        //Tabs
        Tab details = new Tab("Details");
        //Tab images = new Tab("Images");

        Tabs tabs = new Tabs(details);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.addSelectedChangeListener(e -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab.equals(details)) {
                detailsDrawer.setContent(createDetails(grid.getSelectionModel().getFirstSelectedItem().get()));
            } /*else if (selectedTab.equals(images)) {
                detailsDrawer.setContent(createAttachments());
            }*/
        });

        // Header
        detailsDrawerHeader = new DetailsDrawerHeader("", tabs);
        detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addSaveListener(e -> {
        	db.modifyProductData(p, currentName, table);
            detailsDrawer.hide();
            db.retriveAllContent(table);
            grid.setDataProvider(
                    DataProvider.ofCollection(db.getAllContents()));
            UIUtils.showNotification("Changes saved.");
        });
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }
    
    private Button createRemoveButton(Grid<PrimaryContent> grid, PrimaryContent item) {
        @SuppressWarnings("unchecked")
		Button button = new Button(new Icon(VaadinIcon.TRASH), clickEvent -> {
			dataProvider= (ListDataProvider<PrimaryContent>) grid.getDataProvider();
            db.removeData(item.getID(), table);
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        });
        button.setClassName("delete-button");
        button.addThemeName("small");
        return button;
    }

    private void viewDetails(PrimaryContent primaryContent, Grid<PrimaryContent> grid) {
    	this.currentName = primaryContent.id;
    	detailsDrawerHeader.setTitle(primaryContent.getID());
        detailsDrawer.setContent(createDetails(primaryContent));
        detailsDrawer.show();
    }
}
