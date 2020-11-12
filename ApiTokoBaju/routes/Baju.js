const multer = require('multer')
const fs = require('fs')
const router = require('express').Router()
const baju = require('../controller/Baju.js')


var storage = multer.diskStorage({
    filename: function (req, file, cb) {
        let ext = file.originalname.substring(
            file.originalname.lastIndexOf("."),
            file.originalname.length
        )
        cb(null, Date.now() + ext);
    },
    destination: function (req, file, cb) {
        cb(null, './gambar')
    }
})

var upload = multer({storage: storage}).single("gambar")


router.post("/input", upload, (req, res) => {

    baju.inputDataBaju(req.body, req.file.filename)
        .then((result) => res.json(result))
        .catch((err) => res.json(err))
})

router.get("/databaju", (req, res) => {
    baju.lihatDataBaju()
        .then((result) => res.json(result))
        .catch((err) => res.json(err))
})

router.get("/databaju/:id", (req, res) => {
    baju.lihatDetailDataBaju(req.params.id)
        .then((result) => res.json(result))
        .catch((err) => res.json(err))
})

router.delete("/hapus/:id", (req, res) => {
    baju.hapusbaju(req.params.id)
        .then((result) => res.json(result))
        .catch((err) => res.json(err))
})

router.put("/ubah/:id", upload, (req, res)=>{
    let filename;
    if (req.body.gambar) {
        filename = req.body.gambar;
    }else  {
        filename = req.file.filename;
    }
    baju.updateBaju(req.params.id, req.body, filename)
        .then((result) => res.json(result))
        .catch((err) => res.json(err))

})

module.exports = router