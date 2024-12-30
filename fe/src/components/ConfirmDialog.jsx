import React from "react";

export function ConfirmDialog({ open, setOpen, title, content, handleConfirm }) {
  const handleOpen = () => setOpen(!open);

  if (!open) return null;

  return (
    <div style={styles.overlay}>
      <div style={styles.dialog}>
        <div style={styles.header} className="text-start text-xl font-bold">
          <h3>{title}</h3>
        </div>
        <div style={styles.body} className="text-start">
          <p>{content}</p>
        </div>
        <div style={styles.footer}>
          <button style={styles.cancelButton} onClick={handleOpen}>
            キャンセル
          </button>
          <button
            style={styles.confirmButton}
            onClick={() => {
              handleOpen();
              handleConfirm();
            }}
          >
            削除する
          </button>
        </div>
      </div>
    </div>
  );
}

const styles = {
  overlay: {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0.25)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 999
  },
  dialog: {
    backgroundColor: "#fff",
    borderRadius: "8px",
    padding: "20px",
    width: "400px",
    boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
  },
  header: {
    marginBottom: "15px",
  },
  body: {
    marginBottom: "15px",
  },
  footer: {
    display: "flex",
    justifyContent: "flex-end",
  },
  cancelButton: {
    backgroundColor: "#f0f0f0",
    border: "none",
    borderRadius: "4px",
    padding: "8px 12px",
    marginRight: "10px",
    cursor: "pointer",
  },
  confirmButton: {
    backgroundColor: "darkred",
    color: "#fff",
    border: "none",
    borderRadius: "4px",
    padding: "8px 12px",
    cursor: "pointer",
  },
};
