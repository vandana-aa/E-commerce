document.addEventListener('DOMContentLoaded', function () {
    let cart = {};

    // Pagination
    const paginationLinks = document.querySelectorAll('.pagination a');
    paginationLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const page = this.getAttribute('data-page');
            loadProducts(page);
        });
    });

    // Search functionality
    const searchForm = document.getElementById('search-form');
    searchForm.addEventListener('submit', function (event) {
        event.preventDefault();
        const query = document.getElementById('search-input').value;
        searchProducts(query);
    });

    // Load products for a specific page
    function loadProducts(page) {
        fetch(`/products?page=${page}`)
            .then(response => response.json())
            .then(data => {
                renderProducts(data.products);
                renderPagination(data.totalPages, page);
            })
            .catch(error => console.error('Error fetching products:', error));
    }

    // Search products
    function searchProducts(query) {
        fetch(`/products/search?query=${query}`)
            .then(response => response.json())
            .then(data => {
                renderProducts(data.products);
                renderPagination(data.totalPages, 1);
            })
            .catch(error => console.error('Error searching products:', error));
    }

    // Render products to the DOM
    function renderProducts(products) {
        const productsContainer = document.getElementById('products-container');
        productsContainer.innerHTML = '';

        products.forEach(product => {
            const productElement = document.createElement('div');
            productElement.className = 'product';

            productElement.innerHTML = `
                <h3>${product.name}</h3>
                <img src="${product.image}" alt="${product.name}" width="150">
                <p>${product.description}</p>
                <p>Price: $${product.price}</p>
                <button data-id="${product.id}" class="view-details">View Details</button>
                <button data-id="${product.id}" class="add-to-cart">Add to Cart</button>
            `;

            productsContainer.appendChild(productElement);
        });

        // Attach event listeners to view details buttons
        const viewDetailsButtons = document.querySelectorAll('.view-details');
        viewDetailsButtons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.getAttribute('data-id');
                viewProductDetails(productId);
            });
        });

        // Attach event listeners to add to cart buttons
        const addToCartButtons = document.querySelectorAll('.add-to-cart');
        addToCartButtons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.getAttribute('data-id');
                addToCart(productId);
            });
        });
    }

    // Render pagination controls
    function renderPagination(totalPages, currentPage) {
        const paginationContainer = document.getElementById('pagination');
        paginationContainer.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const link = document.createElement('a');
            link.href = '#';
            link.setAttribute('data-page', i);
            link.textContent = i;

            if (i == currentPage) {
                link.classList.add('active');
            }

            paginationContainer.appendChild(link);
        }

        // Attach event listeners to pagination links
        const paginationLinks = document.querySelectorAll('.pagination a');
        paginationLinks.forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                const page = this.getAttribute('data-page');
                loadProducts(page);
            });
        });
    }

    // View product details
    function viewProductDetails(productId) {
        fetch(`/products/${productId}`)
            .then(response => response.json())
            .then(data => {
                const detailsContainer = document.getElementById('product-details');
                detailsContainer.innerHTML = `
                    <h3>${data.name}</h3>
                    <img src="${data.image}" alt="${data.name}" width="150">
                    <p>Description: ${data.description}</p>
                    <p>Features: ${data.features}</p>
                    <p>Price: $${data.price}</p>
                    <p>Available: ${data.available}</p>
                `;
                detailsContainer.style.display = 'block';
            })
            .catch(error => console.error('Error fetching product details:', error));
    }

    // Add product to cart
    function addToCart(productId) {
        fetch(`/products/${productId}`)
            .then(response => response.json())
            .then(data => {
                if (!cart[data.category]) {
                    cart[data.category] = [];
                }
                const productInCart = cart[data.category].find(item => item.id === productId);
                if (productInCart) {
                    productInCart.quantity++;
                } else {
                    cart[data.category].push({
                        id: data.id,
                        name: data.name,
                        price: data.price,
                        quantity: 1
                    });
                }
                renderCart();
            })
            .catch(error => console.error('Error adding product to cart:', error));
    }

    // Render cart
    function renderCart() {
        const cartContainer = document.getElementById('cart-items');
        cartContainer.innerHTML = '';

        for (const category in cart) {
            const categoryDiv = document.createElement('div');
            categoryDiv.className = 'cart-category';
            categoryDiv.innerHTML = `<h3>${category}</h3>`;

            cart[category].forEach(item => {
                const cartItem = document.createElement('div');
                cartItem.className = 'cart-item';

                cartItem.innerHTML = `
                    <p>${item.name} - $${item.price} x ${item.quantity}</p>
                `;

                categoryDiv.appendChild(cartItem);
            });

            cartContainer.appendChild(categoryDiv);
        }

        const checkoutButton = document.getElementById('checkout');
        checkoutButton.style.display = 'block';
    }

    // Checkout
    document.getElementById('checkout').addEventListener('click', function () {
        fetch('/checkout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(cart),
        })
            .then(response => response.json())
            .then(data => {
                alert('Checkout successful!');
                cart = {};
                renderCart();
                disableProducts(data.products);
            })
            .catch(error => console.error('Error during checkout:', error));
    });

    // Disable products after checkout
    function disableProducts(products) {
        products.forEach(product => {
            fetch(`/products/disable/${product.id}`, {
                method: 'PATCH',
            })
                .catch(error => console.error('Error disabling product:', error));
        });
    }

    // Load initial products
    loadProducts(1);
});
