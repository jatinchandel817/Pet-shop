document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("form").forEach(form => {
        form.addEventListener("submit", () => {
            const button = form.querySelector("button[type='submit'], button:not([type])");
            if (button && form.action.includes("/checkout")) {
                button.disabled = true;
                button.textContent = "Placing Order...";
            }
        });
    });
});
