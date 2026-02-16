package packageGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import packageController.WikiDb;

public class MainForm extends JFrame {

    // Top controls
    private JTextField txtKeywords;
    private JButton btnSearch;
    private JButton btnClear;
    private JRadioButton rbDbOnly;
    private JRadioButton rbDbApi;

    // Main tabs
    private JTabbedPane tabsMain;

    // Shared categories model for BOTH tabs
    private DefaultComboBoxModel<String> categoriesModel;

    // Tabs UI (Search + Saved)
    private ArticlesTabUI uiSearch;
    private ArticlesTabUI uiSaved;

    // Stats tab UI (R5)
    private JPanel tabStats;
    private JTable tblTopKeywords;
    private JTable tblSavedByCategory;
    private DefaultTableModel mdlTopKeywords;
    private DefaultTableModel mdlSavedByCategory;
    private JButton btnRefreshStats;
    private JButton btnExportPdf;

    // DB (embedded SQLite for prototype)
    private final WikiDb db = new WikiDb();

    public MainForm() {
        super("Κύρια Φόρμα Αναζήτησης Wiki");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 700));
        setLayout(new BorderLayout());

        // Init shared categories model (used by BOTH tabs)
        categoriesModel = new DefaultComboBoxModel<>(new String[]{"AI", "Προγραμματισμός", "Ιστορία", "java"});


        // categories (shared for Search + Saved tabs) MUST be initialized before building tabs

        setJMenuBar(buildMenu());
        add(buildTopBar(), BorderLayout.NORTH);
        add(buildTabs(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        // categories

        // Apply divider positions AFTER layout is realized
        SwingUtilities.invokeLater(() -> {
            applyDividerLocations(uiSearch);
            applyDividerLocations(uiSaved);
        });

        // seed Search dummy row + comments (Saved tab loads from DB)
        seedDummyRowsAndComments(uiSearch);
        reloadSavedFromDb();
    }

    private JMenuBar buildMenu() {
        JMenuBar mb = new JMenuBar();

        JMenu mSearch = new JMenu("Search Articles");
        JMenuItem miSearch = new JMenuItem("Άνοιγμα");
        miSearch.addActionListener(e -> tabsMain.setSelectedComponent(uiSearch.root));
        mSearch.add(miSearch);

        JMenu mSaved = new JMenu("Saved Articles");
        JMenuItem miSaved = new JMenuItem("Άνοιγμα");
        miSaved.addActionListener(e -> tabsMain.setSelectedComponent(uiSaved.root));
        mSaved.add(miSaved);

        JMenu mStats = new JMenu("Statistics & Keywords");
        JMenuItem miStats = new JMenuItem("Άνοιγμα");
        miStats.addActionListener(e -> {
            // 3rd tab is stats (index 2)
            if (tabsMain != null && tabsMain.getTabCount() >= 3) tabsMain.setSelectedIndex(2);
        });
        mStats.add(miStats);

        JMenu mExit = new JMenu("Exit");
        JMenuItem miExit = new JMenuItem("Έξοδος");
        miExit.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
        mExit.add(miExit);

        mb.add(mSearch);
        mb.add(mSaved);
        mb.add(mStats);
        mb.add(mExit);

        return mb;
    }

    private JComponent buildTopBar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 6, 0, 6);
        c.gridy = 0;

        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        p.add(new JLabel("Keywords"), c);

        txtKeywords = new JTextField();
        txtKeywords.setColumns(35);
        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(txtKeywords, c);

        btnSearch = new JButton("Search");
        c.gridx = 2;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        p.add(btnSearch, c);

        btnClear = new JButton("Clear");
        c.gridx = 3;
        p.add(btnClear, c);

        rbDbOnly = new JRadioButton("DB only");
        rbDbApi = new JRadioButton("DB/API");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbDbOnly);
        bg.add(rbDbApi);
        rbDbApi.setSelected(true); // default

        c.gridx = 4;
        p.add(rbDbOnly, c);
        c.gridx = 5;
        p.add(rbDbApi, c);

        btnClear.addActionListener(e -> {
            txtKeywords.setText("");
            if (uiSearch != null) {
                uiSearch.resultsModel.setRowCount(0);
                uiSearch.txtPreview.setText("");
                uiSearch.selectedArticleId = null;
                uiSearch.starRater.setRating(0);
                uiSearch.currentComments.clear();
                uiSearch.commentsListPanel.removeAll();
                uiSearch.commentsListPanel.revalidate();
                uiSearch.commentsListPanel.repaint();
            }
        });

        btnSearch.addActionListener(e -> {
            // R5: log searched keywords (for Statistics tab)
            db.logSearchTerms(txtKeywords.getText());
            // stub: add one row
            if (uiSearch != null) {
                uiSearch.resultsModel.setRowCount(0);
                uiSearch.resultsModel.addRow(new Object[]{"101", "Wikipedia", "Snippet: results for '" + txtKeywords.getText() + "'"});
            }
        });

        return p;
    }

    private JComponent buildTabs() {
        tabsMain = new JTabbedPane();

        uiSearch = buildArticlesTab(false);
        uiSaved = buildArticlesTab(true);

        tabsMain.addTab("Search Articles", uiSearch.root);
        tabsMain.addTab("Saved Articles", uiSaved.root);
        tabStats = buildStatsTab();
        tabsMain.addTab("Statistics & Keywords", tabStats);

        // IMPORTANT: Top radio selection depends on selected tab
        tabsMain.addChangeListener(e -> {
            if (tabsMain.getSelectedComponent() == uiSaved.root) {
                rbDbOnly.setSelected(true);
                reloadSavedFromDb();
            } else if (tabsMain.getSelectedComponent() == uiSearch.root) {
                rbDbApi.setSelected(true);
            }
        });

        return tabsMain;
    }

    /**
     * R5 - Statistics & Keywords tab.
     * Shows:
     *  (a) Top searched keywords (keyword, count)
     *  (b) Saved articles count per category (category, count)
     *  + export to PDF.
     */
    private JPanel buildStatsTab() {
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Top action bar
        JPanel pTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        btnRefreshStats = new JButton("Refresh");
        btnExportPdf = new JButton("Export PDF");
        pTop.add(btnRefreshStats);
        pTop.add(btnExportPdf);
        root.add(pTop, BorderLayout.NORTH);

        // Models
        mdlTopKeywords = new DefaultTableModel(new Object[]{"Keyword", "Count"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        mdlSavedByCategory = new DefaultTableModel(new Object[]{"Category", "Saved"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblTopKeywords = new JTable(mdlTopKeywords);
        tblSavedByCategory = new JTable(mdlSavedByCategory);
        tblTopKeywords.getTableHeader().setReorderingAllowed(false);
        tblSavedByCategory.getTableHeader().setReorderingAllowed(false);

        JScrollPane sp1 = new JScrollPane(tblTopKeywords);
        sp1.setBorder(BorderFactory.createTitledBorder("Top Keywords (αναζητήσεις χρήστη)"));
        JScrollPane sp2 = new JScrollPane(tblSavedByCategory);
        sp2.setBorder(BorderFactory.createTitledBorder("Saved Articles ανά Κατηγορία"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp1, sp2);
        split.setResizeWeight(0.5);
        split.setOneTouchExpandable(false);
        root.add(split, BorderLayout.CENTER);

        btnRefreshStats.addActionListener(e -> refreshStatsTables());
        btnExportPdf.addActionListener(e -> exportStatsToPdf());

        // initial load
        SwingUtilities.invokeLater(this::refreshStatsTables);
        return root;
    }

    private void refreshStatsTables() {
        if (mdlTopKeywords == null || mdlSavedByCategory == null) return;
        try {
            // top keywords
            mdlTopKeywords.setRowCount(0);
            for (WikiDb.KeywordStat s : db.getTopKeywords(30)) {
                mdlTopKeywords.addRow(new Object[]{s.keyword(), s.count()});
            }

            // saved per category
            mdlSavedByCategory.setRowCount(0);
            for (WikiDb.CategoryStat s : db.getSavedCountByCategory()) {
                mdlSavedByCategory.addRow(new Object[]{s.category(), s.count()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Αποτυχία φόρτωσης στατιστικών: " + ex.getMessage(),
                    "Statistics", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Export stats to a simple PDF using PDFBox.
     * If PDFBox isn't available for any reason, falls back to printing.
     */
    private void exportStatsToPdf() {
        // Choose file
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Export Statistics to PDF");
        fc.setSelectedFile(new File("stats.pdf"));
        int res = fc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) return;
        File out = fc.getSelectedFile();

        try {
            PdfExportUtil.exportTablesToPdf(out, tblTopKeywords, tblSavedByCategory);
            JOptionPane.showMessageDialog(this, "Έγινε εξαγωγή PDF: " + out.getAbsolutePath(),
                    "Export PDF", JOptionPane.INFORMATION_MESSAGE);
        } catch (Throwable t) {
            // fallback: print (user can select Microsoft Print to PDF)
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Δεν ήταν δυνατή η αυτόματη δημιουργία PDF (" + t.getClass().getSimpleName() + ").\n" +
                            "Θες να ανοίξει εκτύπωση (Print) ώστε να αποθηκεύσεις σε PDF;",
                    "Export PDF", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            try {
                tblTopKeywords.print();
                tblSavedByCategory.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Αποτυχία Print: " + ex.getMessage(), "Print", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ArticlesTabUI buildArticlesTab(boolean isSavedTab) {
        ArticlesTabUI ui = new ArticlesTabUI();
        ui.isSavedTab = isSavedTab;
        ui.root = new JPanel(new BorderLayout());

        // Left: categories
        JPanel pLeft = new JPanel();
        pLeft.setLayout(new BorderLayout(0, 8));
        pLeft.setBorder(new EmptyBorder(8, 8, 8, 8));

        ui.cbCategory = new JComboBox<>(categoriesModel);
        ui.cbCategory.setEditable(false);
        pLeft.add(ui.cbCategory, BorderLayout.NORTH);

        JPanel pLeftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        ui.btnAddCategory = new JButton("+ Κατηγορία");
        ui.btnDeleteCategory = new JButton("− Διαγραφή");
        pLeftButtons.add(ui.btnAddCategory);
        pLeftButtons.add(ui.btnDeleteCategory);
        pLeft.add(pLeftButtons, BorderLayout.CENTER);

        // Category add/delete behavior (shared model)
        ui.btnAddCategory.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Νέα Κατηγορία:", "Προσθήκη Κατηγορίας", JOptionPane.QUESTION_MESSAGE);
            if (name == null) return;
            name = name.trim();
            if (name.isEmpty()) return;
            // add only if not exists
            for (int i = 0; i < categoriesModel.getSize(); i++) {
                if (name.equalsIgnoreCase(categoriesModel.getElementAt(i))) {
                    ui.cbCategory.setSelectedIndex(i);
                    return;
                }
            }
            categoriesModel.addElement(name);
            ui.cbCategory.setSelectedItem(name);
        });

        ui.btnDeleteCategory.addActionListener(e -> {
            Object sel = ui.cbCategory.getSelectedItem();
            if (sel == null) return;
            if (categoriesModel.getSize() <= 1) {
                JOptionPane.showMessageDialog(this, "Δεν μπορείς να διαγράψεις την τελευταία κατηγορία.");
                return;
            }
            String s = String.valueOf(sel);
            int confirm = JOptionPane.showConfirmDialog(this, "Διαγραφή κατηγορίας: " + s + " ?", "Διαγραφή", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            // remove from shared model
            for (int i = 0; i < categoriesModel.getSize(); i++) {
                if (s.equals(categoriesModel.getElementAt(i))) {
                    categoriesModel.removeElementAt(i);
                    break;
                }
            }
        });

        // Middle: results table
        ui.resultsModel = new DefaultTableModel(new Object[]{"Id", "Title", "Snippet"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        ui.tblResults = new JTable(ui.resultsModel);
        ui.tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ui.tblResults.getTableHeader().setReorderingAllowed(false);
        ui.tblResults.getColumnModel().getColumn(0).setPreferredWidth(70);
        ui.tblResults.getColumnModel().getColumn(1).setPreferredWidth(220);
        ui.tblResults.getColumnModel().getColumn(2).setPreferredWidth(450);
        JScrollPane spTable = new JScrollPane(ui.tblResults);

        // Right: preview
        ui.txtPreview = new JTextArea();
        ui.txtPreview.setEditable(false);
        ui.txtPreview.setLineWrap(true);
        ui.txtPreview.setWrapStyleWord(true);
        JScrollPane spPreview = new JScrollPane(ui.txtPreview);

        // Comments area (matches your screenshot style)
        JPanel commentsRoot = buildCommentsPanel(ui);

        // Right vertical split: preview / comments
        ui.splitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spPreview, commentsRoot);
        ui.splitRight.setResizeWeight(0.65);
        ui.splitRight.setOneTouchExpandable(false);

        // Center split: table / right
        ui.splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spTable, ui.splitRight);
        ui.splitCenter.setResizeWeight(0.40);
        ui.splitCenter.setOneTouchExpandable(false);

        // Outer split: left / (center)
        ui.splitOuter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pLeft, ui.splitCenter);
        ui.splitOuter.setResizeWeight(0.0);
        ui.splitOuter.setOneTouchExpandable(false);

        // Bottom bar (IMPORTANT: always visible)
        ui.bottomBar = buildBottomBar(ui);

        ui.root.add(ui.splitOuter, BorderLayout.CENTER);
        ui.root.add(ui.bottomBar, BorderLayout.SOUTH);

        // Selection wiring
        ui.tblResults.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = ui.tblResults.getSelectedRow();
            if (row < 0) return;
            ui.selectedArticleId = String.valueOf(ui.resultsModel.getValueAt(row, 0));
            String title = String.valueOf(ui.resultsModel.getValueAt(row, 1));
            String snippet = String.valueOf(ui.resultsModel.getValueAt(row, 2));
            ui.txtPreview.setText("# " + title + "\n\n" + "[ArticleId=" + ui.selectedArticleId + "]\n\n" + snippet + "\n\n" + dummyLongText());

            // if exists in DB, load rating + comments
            try {
                WikiDb.SavedArticle sa = db.getSavedArticle(ui.selectedArticleId);
                if (sa != null) {
                    ui.starRater.setRating(sa.rating());
                    if (sa.category() != null && !sa.category().isBlank()) {
                        ui.cbCategory.setSelectedItem(sa.category());
                    }
                    loadCommentsFromDb(ui, ui.selectedArticleId);
                }
            } catch (Exception ex) {
                // ignore
            }
        });

        return ui;
    }

    private JPanel buildBottomBar(ArticlesTabUI ui) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        p.setBorder(new EmptyBorder(4, 8, 6, 8));

        ui.btnViewDetails = new JButton("View Details");
        ui.btnSave = new JButton("Save");

        JLabel lblRating = new JLabel("Βαθμολογία");
        ui.starRater = new StarRater(5);

        p.add(ui.btnViewDetails);
        p.add(ui.btnSave);
        p.add(Box.createHorizontalStrut(15));
        p.add(lblRating);
        p.add(ui.starRater);

        ui.btnViewDetails.addActionListener(e -> openDetailsDialog(ui));
        ui.btnSave.addActionListener(e -> {
            if (ui.selectedArticleId == null) {
                JOptionPane.showMessageDialog(this, "Διάλεξε πρώτα άρθρο.", "Save", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Save to DB: {Id, Title, Snippet, Category, Comments, Rating}
            try {
                int row = ui.tblResults.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this, "Διάλεξε πρώτα άρθρο.", "Save", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String id = String.valueOf(ui.resultsModel.getValueAt(row, 0));
                String title = String.valueOf(ui.resultsModel.getValueAt(row, 1));
                String snippet = String.valueOf(ui.resultsModel.getValueAt(row, 2));
                String category = String.valueOf(ui.cbCategory.getSelectedItem());
                int rating = ui.starRater.getRating();

                db.upsertSavedArticle(new WikiDb.SavedArticle(id, title, snippet, category, rating));
                db.replaceComments(id, ui.currentComments);

                JOptionPane.showMessageDialog(this,
                        "Αποθηκεύτηκε στη ΒΔ:\n" +
                                "Id=" + id + "\n" +
                                "Title=" + title + "\n" +
                                "Category=" + category + "\n" +
                                "Rating=" + rating + "\n" +
                                "Σχόλια=" + ui.currentComments.size(),
                        "Save", JOptionPane.INFORMATION_MESSAGE);

                // refresh Saved tab list
                reloadSavedFromDb();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Αποτυχία Save στη ΒΔ: " + ex.getMessage(), "Save", JOptionPane.ERROR_MESSAGE);
            }
        });

        return p;
    }

    private JPanel buildCommentsPanel(ArticlesTabUI ui) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder("Σχόλια"));

        ui.commentsListPanel = new JPanel();
        ui.commentsListPanel.setLayout(new BoxLayout(ui.commentsListPanel, BoxLayout.Y_AXIS));

        // scroll for list
        ui.spComments = new JScrollPane(ui.commentsListPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // new comment area
        JPanel pNew = new JPanel(new BorderLayout(6, 6));
        pNew.setBorder(new EmptyBorder(6, 6, 6, 6));

        JPanel pNewTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        pNewTop.add(new JLabel("Χρήστης"));
        ui.txtNewUser = new JTextField("", 12);
        pNewTop.add(ui.txtNewUser);
        ui.btnAddComment = new JButton("Προσθήκη");
        pNewTop.add(ui.btnAddComment);
        pNew.add(pNewTop, BorderLayout.NORTH);

        ui.txtNewComment = new JTextArea(3, 30);
        ui.txtNewComment.setLineWrap(true);
        ui.txtNewComment.setWrapStyleWord(true);
        JScrollPane spNewComment = new JScrollPane(ui.txtNewComment,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pNew.add(spNewComment, BorderLayout.CENTER);

        ui.btnAddComment.addActionListener(e -> {
            String u = ui.txtNewUser.getText().trim();
            String t = ui.txtNewComment.getText().trim();
            if (u.isEmpty() || t.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Συμπλήρωσε Χρήστη και Σχόλιο.", "Σχόλια", JOptionPane.WARNING_MESSAGE);
                return;
            }
            addCommentCard(ui, u, t);
            ui.txtNewComment.setText("");
            // scroll to bottom
            SwingUtilities.invokeLater(() -> {
                JScrollBar bar = ui.spComments.getVerticalScrollBar();
                bar.setValue(bar.getMaximum());
            });
        });

        wrapper.add(ui.spComments, BorderLayout.CENTER);
        wrapper.add(pNew, BorderLayout.SOUTH);

        // ensure comments area has the "right" size like screenshot
        wrapper.setPreferredSize(new Dimension(600, 260));

        return wrapper;
    }

    private void addCommentCard(ArticlesTabUI ui, String user, String text) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel lblUser = new JLabel(user);
        lblUser.setFont(lblUser.getFont().deriveFont(Font.BOLD));
        JTextArea ta = new JTextArea(text);
        ta.setEditable(false);
        ta.setOpaque(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        card.add(lblUser, BorderLayout.NORTH);
        card.add(ta, BorderLayout.CENTER);

        // separator line
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);

        ui.commentsListPanel.add(card);
        ui.commentsListPanel.add(sep);

        // keep in-memory list (so Save persists to DB)
        ui.currentComments.add(new WikiDb.Comment(user, text, null));

        ui.commentsListPanel.revalidate();
        ui.commentsListPanel.repaint();
    }

    private void openDetailsDialog(ArticlesTabUI ui) {
        JDialog d = new JDialog(this, "View Details", false);
        d.setLayout(new BorderLayout(8, 8));
        d.setSize(900, 650);
        d.setLocationRelativeTo(this);

        JTextArea ta = new JTextArea(ui.txtPreview.getText());
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        JScrollPane sp = new JScrollPane(ta);

        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(e -> {
            try {
                ta.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(d, "Αποτυχία εκτύπωσης: " + ex.getMessage());
            }
        });

        JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSouth.add(btnPrint);

        d.add(sp, BorderLayout.CENTER);
        d.add(pSouth, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private void applyDividerLocations(ArticlesTabUI ui) {
        if (ui == null) return;
        // Exact-ish widths like screenshot
        ui.splitOuter.setDividerLocation(270);
        ui.splitCenter.setDividerLocation(460); // table width
        ui.splitRight.setDividerLocation((int) (getHeight() * 0.55));
    }

    private void seedDummyRowsAndComments(ArticlesTabUI ui) {
        ui.resultsModel.setRowCount(0);
        ui.resultsModel.addRow(new Object[]{"1", "Title", "Snippet"});

        ui.currentComments.clear();
        ui.commentsListPanel.removeAll();
        addCommentCard(ui, "Χρήστης A", "Πολύ καλό άρθρο, περιεκτικό.");
        addCommentCard(ui, "Χρήστης B", "Θέλει καλύτερες πηγές, αλλά χρήσιμο.");
        ui.commentsListPanel.revalidate();
        ui.commentsListPanel.repaint();
    }

    private void reloadSavedFromDb() {
        if (uiSaved == null) return;
        try {
            uiSaved.resultsModel.setRowCount(0);
            for (WikiDb.SavedArticle a : db.listSavedArticles()) {
                uiSaved.resultsModel.addRow(new Object[]{a.id(), a.title(), a.snippet()});
            }
        } catch (Exception ex) {
            // ignore (prototype should still run)
        }
    }

    private void loadCommentsFromDb(ArticlesTabUI ui, String articleId) {
        try {
            ui.currentComments.clear();
            ui.commentsListPanel.removeAll();
            List<WikiDb.Comment> cms = db.listComments(articleId);
            for (WikiDb.Comment c : cms) {
                // reuse cards and in-memory list
                addCommentCard(ui, c.username(), c.body());
            }
            // addCommentCard adds to currentComments; so we are consistent.
            ui.commentsListPanel.revalidate();
            ui.commentsListPanel.repaint();
        } catch (Exception ex) {
            // ignore
        }
    }

    private static class ArticlesTabUI {
        boolean isSavedTab;
        JPanel root;
        JSplitPane splitOuter;
        JSplitPane splitCenter;
        JSplitPane splitRight;
        JComboBox<String> cbCategory;
        JButton btnAddCategory;
        JButton btnDeleteCategory;
        JTable tblResults;
        DefaultTableModel resultsModel;
        JTextArea txtPreview;
        JScrollPane spComments;
        JPanel commentsListPanel;
        JTextArea txtNewComment;
        JTextField txtNewUser;
        JButton btnAddComment;
        JPanel bottomBar;
        JButton btnViewDetails;
        JButton btnSave;
        StarRater starRater;
        String selectedArticleId;
        final List<WikiDb.Comment> currentComments = new ArrayList<>();
    }

    private String dummyLongText() {
        return "\n\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(25) +
                "\n";
    }

    // Simple star rater (0..max)
    private static class StarRater extends JPanel {
        private final int max;
        private final List<JToggleButton> stars = new ArrayList<>();

        StarRater(int max) {
            super(new FlowLayout(FlowLayout.LEFT, 2, 0));
            this.max = max;
            setOpaque(false);
            for (int i = 1; i <= max; i++) {
                final int rating = i;
                JToggleButton b = new JToggleButton("☆");
                b.setMargin(new Insets(0, 2, 0, 2));
                b.setFocusPainted(false);
                b.setBorderPainted(false);
                b.setContentAreaFilled(false);
                b.setFont(b.getFont().deriveFont(Font.PLAIN, 18f));
                b.addActionListener(e -> setRating(rating));
                stars.add(b);
                add(b);
            }
            setRating(0);
        }

        int getRating() {
            int r = 0;
            for (int i = 0; i < stars.size(); i++) {
                if (stars.get(i).isSelected()) r = i + 1;
            }
            return r;
        }

        void setRating(int r) {
            for (int i = 0; i < stars.size(); i++) {
                boolean on = i < r;
                stars.get(i).setSelected(on);
                stars.get(i).setText(on ? "★" : "☆");
            }
        }
    }
}