class ArticlesController < ApplicationController
  # GET /articles
  # GET /articles.json
  def index
    @articles = Article.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @articles }
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
    
    respond_to do |format|
      if @article.save && !params[:stores].nil?
          @stores = Store.find(params[:stores])
          @stores.each { |s|
            @article.stores << s
          }
        
        format.html { redirect_to @article, notice: 'Article was successfully created.' }
        format.json { render json: @article, status: :created, location: @article }
      else
        format.html { render action: "new" }
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
    
    if !params[:stores].nil?
      @stores = Store.find(params[:stores])
      @stores.each { |s|
        @article.stores.delete(s) # prohibit duplicate references to store
        @article.stores << s
      }
    end
    
    respond_to do |format|
      if @article.update_attributes(params[:article])
        format.html { redirect_to @article, notice: 'Article was successfully updated.' }
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
  
  def delete_store_ref
    @article = Article.find(params[:id])
    @store = Store.find(params[:store_id])
    
    @article.stores.delete(@store)
    
    respond_to do |format|
      format.html { redirect_to @article }
      format.json { head :no_content }
    end
  end
end
