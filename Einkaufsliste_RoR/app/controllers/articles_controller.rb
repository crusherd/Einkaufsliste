class ArticlesController < ApplicationController
  # GET /articles
  # GET /articles.json
  def index
    @articles = Article.order("name ASC, price ASC").all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @articles, :include => :stores }
    end
  end

  # GET /articles/1
  # GET /articles/1.json
  def show
    @article = Article.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @article }
    end
  end

  # GET /articles/new
  # GET /articles/new.json
  def new
    @article = Article.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @article }
    end
  end

  # GET /articles/1/edit
  def edit
    @article = Article.find(params[:id])
  end

  # POST /articles
  # POST /articles.json
  def create
    @article = Article.new(params[:article])
    if !params[:stores].nil?
      # prevent assignment of empty element to stores reference
      params[:stores].delete_if { |x| x == ""}
    end
    
    if @article.price.nil?
      # user don't know the price of the article yet
      @article.price = 0
    end
    
    respond_to do |format|
      if @article.save
        if !params[:stores].nil?
          @stores = Store.find(params[:stores])
          @stores.each { |s|
            @article.stores << s
          }
        end
        
        format.html { redirect_to articles_path, notice: 'Article was successfully created.' }
        format.json { render json: @article, status: :created, location: @article }
      else
        format.html { render action: "new" }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end
  
  def new_store_article_ref
    @article = Article.new
    @current_store = Store.find_by_id(:store_id)
    
    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @article }
    end
  end
  
  def create_store_article_ref
    @current_store = Store.find_by_id(params[:store_id])
    @article = Article.new(params[:article])
    
    @current_store.articles << @article
    
    respond_to do |format|
      if @article.save
        format.html { redirect_to stores_path, notice: 'Address was successfully created.' }
        format.json { render json: @current_store, status: :created, location: @current_store }
      else
        format.html { render action: "new_store_article_ref" }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end
  
  def add_store_article_ref
    @article = Article.new
    @current_store = Store.find_by_id(params[:store_id])
    @article_id = params[:article_id]
    
    respond_to do |format|
      if @article_id != ""
        @article = Article.find_by_id(@article_id)
        @current_store.articles.delete(@article) # prohibit duplicate references to store
        @current_store.articles << @article
        
        format.html { redirect_to stores_path, notice: 'Address was successfully created.' }
        format.json { render json: @current_store, status: :created, location: @current_store }
      else
        format.html { render action: "new_store_article_ref" }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /articles/1
  # PUT /articles/1.json
  def update
    @article = Article.find(params[:id])
    
    if !params[:stores].nil?
      # prevent assignment of empty element to stores reference
      params[:stores].delete_if { |x| x == ""}
    end
    
    if @article.price.nil?
      # user don't know the price of the article yet
      @article.price = 0
    end
    
    if !params[:stores].nil?
      @stores = Store.find(params[:stores])
      @stores.each { |s|
        @article.stores.delete(s) # prohibit duplicate references to store
        @article.stores << s
      }
    end
    
    respond_to do |format|
      if @article.update_attributes(params[:article])
        if params[:list_id].empty?
          format.html { redirect_to articles_path, notice: 'Article was successfully updated.' }
        else
          format.html { redirect_to select_shoppinglist_path(params[:list_id]), notice: 'Article was successfully updated.' }
        end
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /articles/1
  # DELETE /articles/1.json
  def destroy
    @article = Article.find(params[:id])
    @article.destroy

    respond_to do |format|
      format.html { redirect_to articles_url }
      format.json { head :no_content }
    end
  end
  
  def add_store
    @current_article = Article.find(params[:id])
    redirect_to new_article_store_ref_path(@current_article)
  end
  
  def delete_store_ref
    @article = Article.find(params[:id])
    @store = Store.find(params[:store_id])
    
    @article.stores.delete(@store)
    
    respond_to do |format|
      format.html { redirect_to articles_path }
      format.json { head :no_content }
    end
  end
end
